package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Author {
    private int authorId;
    private String authorName;
}
