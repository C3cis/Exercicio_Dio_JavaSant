package service;

import dao.BoardDAO;
import dao.BoardColumnDAO;
import entity.BoardColumnEntity;
import entity.BoardEntity;
import enums.BoardColumnKindEnum;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {
    private final Connection connection;

    public BoardEntity createBoard(String name) throws SQLException {
        var boardDao = new BoardDAO(connection);
        var colDao = new BoardColumnDAO(connection);

        try {
            // cria board
            BoardEntity board = new BoardEntity();
            board.setName(name);
            boardDao.insert(board);

            // cria colunas padrão:
            BoardColumnEntity initial = new BoardColumnEntity();
            initial.setBoardId(board.getId());
            initial.setName("Inicial");
            initial.setOrder(1);
            initial.setKind(BoardColumnKindEnum.INITIAL);
            colDao.insert(initial);

            BoardColumnEntity pending = new BoardColumnEntity();
            pending.setBoardId(board.getId());
            pending.setName("Pendentes");
            pending.setOrder(2);
            pending.setKind(BoardColumnKindEnum.PENDING);
            colDao.insert(pending);

            BoardColumnEntity finished = new BoardColumnEntity();
            finished.setBoardId(board.getId());
            finished.setName("Finalizado");
            finished.setOrder(3);
            finished.setKind(BoardColumnKindEnum.FINAL);
            colDao.insert(finished);

            BoardColumnEntity canceled = new BoardColumnEntity();
            canceled.setBoardId(board.getId());
            canceled.setName("Cancelado");
            canceled.setOrder(4);
            canceled.setKind(BoardColumnKindEnum.CANCEL);
            colDao.insert(canceled);

            connection.commit();
            return board;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public boolean delete(Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        try {
            if (!dao.exists(id)) return false;
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public java.util.List<BoardEntity> list() throws SQLException {
        var dao = new BoardDAO(connection);
        return dao.findAll();
    }

    public boolean updateBoard(Long id, String newName) throws SQLException {
        var dao = new BoardDAO(connection);
        try {
            if (!dao.exists(id)) return false;
            BoardEntity b = new BoardEntity();
            b.setId(id);
            b.setName(newName);
            dao.update(b);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
