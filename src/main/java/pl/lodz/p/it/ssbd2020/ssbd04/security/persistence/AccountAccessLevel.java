package pl.lodz.p.it.ssbd2020.ssbd04.security.persistence;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account_access_level")
public class AccountAccessLevel implements Serializable {
    @Id
    private Long id;

    @Column(name = "access_level")
    String name;

    public AccountAccessLevel() {
    }

    public String getName() {
        return name;
    }
}