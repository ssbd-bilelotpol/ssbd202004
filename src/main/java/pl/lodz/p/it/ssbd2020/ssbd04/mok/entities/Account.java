package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels.AccountAccessLevel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Klasa encyjna zawierająca informacje o koncie użytkownika
 */
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(unique = true, nullable = false, length = 30)
    private String login;

    @NotNull
    @Size(min = 50, max = 60)
    @Column(nullable = false, length = 60)
    private String password;

    @NotNull
    @Column(nullable = false)
    private Boolean active;

    @NotNull
    @Column(nullable = false)
    private Boolean confirm;

    @OneToMany
    @JoinColumn(name = "account_id")
    private Set<AccountAccessLevel> accountAccessLevel;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_details_id", unique = true, updatable = false)
    private AccountDetails accountDetails;

    @Version
    private Long version;

    public Account() {
    }

    public Account(@NotNull @Size(min = 3, max = 30) String login, @NotNull @Size(min = 50, max = 60) String password,
                   @NotNull Boolean active, @NotNull Boolean confirm, Set<AccountAccessLevel> accountAccessLevel,
                   AccountDetails accountDetails) {
        this.login = login;
        this.password = password;
        this.active = active;
        this.confirm = confirm;
        this.accountAccessLevel = accountAccessLevel;
        this.accountDetails = accountDetails;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<AccountAccessLevel> getAccountAccessLevel() {
        return accountAccessLevel;
    }

    public void setAccountAccessLevel(Set<AccountAccessLevel> accountAccessLevel) {
        this.accountAccessLevel = accountAccessLevel;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        return Objects.equals(login, account.login);
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
