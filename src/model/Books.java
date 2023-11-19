package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Books {
    private int bookId;
    private String title;
    private String description;
    private int authorId;
    private boolean borrowed;
}
