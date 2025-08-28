package dao;

import entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CardDAO {
    private final Connection connection;

    public CardEntity insert(CardEntity entity) throws SQLException {
        String sql = "INSERT INTO CARDS (title, description, column_id, blocked, created_at) VALUES (?,?,?,?,NOW())";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setLong(3, entity.getColumnId());
            ps.setBoolean(4, entity.isBlocked());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    public CardEntity findById(Long id) throws SQLException {
        String sql = "SELECT id, title, description, column_id, blocked, created_at FROM CARDS WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CardEntity c = new CardEntity();
                    c.setId(rs.getLong("id"));
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("description"));
                    c.setColumnId(rs.getLong("column_id"));
                    c.setBlocked(rs.getBoolean("blocked"));
                    c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return c;
                }
            }
        }
        return null;
    }

    public List<CardEntity> findByColumn(Long columnId) throws SQLException {
        String sql = "SELECT id, title, description, created_at, blocked, column_id FROM CARDS WHERE column_id = ? ORDER BY created_at";
        List<CardEntity> cards = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, columnId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CardEntity c = new CardEntity();
                    c.setId(rs.getLong("id"));
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("description"));
                    c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    c.setBlocked(rs.getBoolean("blocked"));
                    c.setColumnId(rs.getLong("column_id"));
                    cards.add(c);
                }
            }
        }
        return cards;
    }

    public void move(Long cardId, Long newColumnId) throws SQLException {
        String sql = "UPDATE CARDS SET column_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, newColumnId);
            ps.setLong(2, cardId);
            ps.executeUpdate();
        }
    }

    public void updateBlocked(Long cardId, boolean blocked) throws SQLException {
        String sql = "UPDATE CARDS SET blocked = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, blocked);
            ps.setLong(2, cardId);
            ps.executeUpdate();
        }
    }
}
