package model;

import lombok.*;

@Getter
@Setter
public class Client extends User{
    private String clientStatus;
    public Client(int id, String firstName, String lastName, String adress, String number) {
        super(id, firstName, lastName, adress, number);
        this.clientStatus = clientStatus;
    }
}
