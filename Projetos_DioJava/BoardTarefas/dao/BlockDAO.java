package dao;

import entity.BlockEntity;
import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class BlockDAO {
    private final Connection connection;

    public void insert(BlockEntity entity) throws SQLException {
        String sql = "INSERT INTO BLOCKS (card_id, blocked_at, block_reason) VALUES (?, NOW(), ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getCardId());
            ps.setString(2, entity.getBlockReason());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) entity.setId(rs.getLong(1));
            }
        }
    }

    public void unblock(BlockEntity entity) throws SQLException {
        String sql = "UPDATE BLOCKS SET unblocked_at = NOW(), unblock_reason = ? WHERE card_id = ? AND unblocked_at IS NULL";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getUnblockReason());
            ps.setLong(2, entity.getCardId());
            ps.executeUpdate();
        }
    }
}
