package dao;

import entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BoardDAO {
    private final Connection connection;

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    public List<BoardEntity> findAll() throws SQLException {
        String sql = "SELECT id, name FROM BOARDS ORDER BY id";
        List<BoardEntity> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BoardEntity b = new BoardEntity();
                b.setId(rs.getLong("id"));
                b.setName(rs.getString("name"));
                list.add(b);
            }
        }
        return list;
    }

    public boolean exists(Long id) throws SQLException {
        String sql = "SELECT 1 FROM BOARDS WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void update(BoardEntity entity) throws SQLException {
        String sql = "UPDATE BOARDS SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARDS WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
