package entity;

import enums.BoardColumnKindEnum;
import lombok.Data;

@Data
public class BoardColumnEntity {
    private Long id;
    private Long boardId;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
}
