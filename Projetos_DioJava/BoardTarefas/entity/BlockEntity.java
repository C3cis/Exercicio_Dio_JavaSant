package entity;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class BlockEntity {
    private Long id;
    private Long cardId;
    private OffsetDateTime blockedAt;
    private String blockReason;
    private OffsetDateTime unblockedAt;
    private String unblockReason;
}
