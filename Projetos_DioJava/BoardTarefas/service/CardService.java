package service;

import dao.BlockDAO;
import dao.CardDAO;
import dao.BoardColumnDAO;
import entity.BlockEntity;
import entity.CardEntity;
import entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CardService {
    private final Connection connection;

    public CardEntity createCardInInitial(Long boardId, String title, String description) throws SQLException {
        var colDao = new BoardColumnDAO(connection);
        var cardDao = new CardDAO(connection);
        try {
            List<BoardColumnEntity> cols = colDao.findByBoardId(boardId);
            Optional<BoardColumnEntity> initialOpt = cols.stream()
                    .filter(c -> c.getKind() != null && c.getKind().name().equals("INITIAL"))
                    .findFirst();

            if (initialOpt.isEmpty()) {
                throw new IllegalStateException("Board sem coluna INITIAL");
            }

            CardEntity card = new CardEntity();
            card.setTitle(title);
            card.setDescription(description);
            card.setColumnId(initialOpt.get().getId());
            card.setBlocked(false);

            cardDao.insert(card);
            connection.commit();
            return card;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public List<CardEntity> listByColumn(Long columnId) throws SQLException {
        var cardDao = new CardDAO(connection);
        return cardDao.findByColumn(columnId);
    }

    public boolean moveToNext(Long cardId) throws SQLException {
        var cardDao = new CardDAO(connection);
        var colDao = new BoardColumnDAO(connection);
        try {
            CardEntity card = cardDao.findById(cardId);
            if (card == null) return false;
            if (card.isBlocked()) return false;

            Long currentColumnId = card.getColumnId();

            String sql = "SELECT board_id, `order` FROM BOARDS_COLUMNS WHERE id = ?";
            try (java.sql.PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, currentColumnId);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new IllegalStateException("Coluna atual não encontrada");
                    Long boardId = rs.getLong("board_id");
                    int currOrder = rs.getInt("order");

                    String sql2 = "SELECT id, kind FROM BOARDS_COLUMNS WHERE board_id = ? AND `order` = ?";
                    try (java.sql.PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                        ps2.setLong(1, boardId);
                        ps2.setInt(2, currOrder + 1);
                        try (java.sql.ResultSet rs2 = ps2.executeQuery()) {
                            if (!rs2.next()) {
                                return false;
                            }
                            Long nextColId = rs2.getLong("id");
                            String nextKind = rs2.getString("kind");
                        
                            cardDao.move(cardId, nextColId);
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public boolean moveToCancel(Long cardId) throws SQLException {
        var cardDao = new CardDAO(connection);
        try {
            CardEntity card = cardDao.findById(cardId);
            if (card == null) return false;
            if (card.isBlocked()) return false;

            // buscar ultima coluna (CANCEL) do board
            String sql = "SELECT bc.id, bc.kind FROM BOARDS_COLUMNS bc " +
                    "JOIN CARDS c ON c.column_id IS NOT NULL " +
                    "WHERE bc.board_id = (SELECT board_id FROM BOARDS_COLUMNS WHERE id = (SELECT column_id FROM CARDS WHERE id = ?)) " +
                    "AND bc.kind = 'CANCEL' LIMIT 1";

            try (java.sql.PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, cardId);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Long cancelColId = rs.getLong("id");
                        cardDao.move(cardId, cancelColId);
                        connection.commit();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public boolean blockCard(Long cardId, String reason) throws SQLException {
        var cardDao = new CardDAO(connection);
        var blockDao = new BlockDAO(connection);
        try {
            CardEntity card = cardDao.findById(cardId);
            if (card == null) return false;
            cardDao.updateBlocked(cardId, true);

            BlockEntity b = new BlockEntity();
            b.setCardId(cardId);
            b.setBlockReason(reason);
            blockDao.insert(b);

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public boolean unblockCard(Long cardId, String reason) throws SQLException {
        var cardDao = new CardDAO(connection);
        var blockDao = new BlockDAO(connection);
        try {
            CardEntity card = cardDao.findById(cardId);
            if (card == null) return false;
            cardDao.updateBlocked(cardId, false);

            BlockEntity b = new BlockEntity();
            b.setCardId(cardId);
            b.setUnblockReason(reason);
            blockDao.unblock(b);

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
