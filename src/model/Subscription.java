package model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Subscription {
    private int subscriptionId;
    private int userId;
    private int bookId;
    private Date dateBorrowed;
    private Date dateReturned;
}
