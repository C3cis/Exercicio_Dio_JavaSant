package entity;

import lombok.Data;
import java.util.List;

@Data
public class BoardEntity {
    private Long id;
    private String name;
    private List<BoardColumnEntity> columns;
}
