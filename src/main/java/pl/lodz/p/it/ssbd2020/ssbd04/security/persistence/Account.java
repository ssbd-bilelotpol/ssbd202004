package pl.lodz.p.it.ssbd2020.ssbd04.security.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Account implements Serializable {
    @Id
    private Long id;
    private String login;
    private Boolean active;
    private String password;

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<AccountAccessLevel> accountAccessLevel;

    public String getPassword() {
        return password;
    }

    public Set<AccountAccessLevel> getAccountAccessLevel() {
        return accountAccessLevel;
    }
}