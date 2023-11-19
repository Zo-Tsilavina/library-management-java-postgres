package model;

import lombok.*;

@Getter
@Setter
public class Administrator extends User{
    private String administratorRole;
    public Administrator(int id, String firstName, String lastName, String adress, String number) {
        super(id, firstName, lastName, adress, number);
        this.administratorRole = administratorRole;
    }
}
