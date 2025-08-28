package dao;

import entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BoardColumnDAO {
    private final Connection connection;

    public BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, kind, board_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getOrder());
            ps.setString(3, entity.getKind().name());
            ps.setLong(4, entity.getBoardId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getLong(1));
            }
        }
        return entity;
    }

    public List<BoardColumnEntity> findByBoardId(Long boardId) throws SQLException {
        String sql = "SELECT id, name, `order`, kind FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`";
        List<BoardColumnEntity> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, boardId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BoardColumnEntity c = new BoardColumnEntity();
                    c.setId(rs.getLong("id"));
                    c.setBoardId(boardId);
                    c.setName(rs.getString("name"));
                    c.setOrder(rs.getInt("order"));
                    c.setKind(javaEnum(rs.getString("kind")));
                    list.add(c);
                }
            }
        }
        return list;
    }

    private BoardColumnEntity.Kind javaEnum(String kindStr) { return null; } 

}
