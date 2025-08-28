package entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CardEntity {
    private Long id;
    private Long columnId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private boolean blocked;
}
