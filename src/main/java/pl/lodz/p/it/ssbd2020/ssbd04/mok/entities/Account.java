package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels.AccountAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account.CONSTRAINT_LOGIN;

/**
 * Klasa encyjna zawierająca informacje o koncie użytkownika
 */
@NamedQueries({
        @NamedQuery(name = "Account.findByLogin",
                query = "SELECT account FROM Account account WHERE account.login = :login"),
        @NamedQuery(name = "Account.findByEmail",
                query = "SELECT account FROM Account account JOIN account.accountDetails ad WHERE ad.email = :email")
})
@Entity
@Table(
        indexes = {@Index(name = "account_account_details_fk", columnList = "account_details_id")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login", name = CONSTRAINT_LOGIN),
                @UniqueConstraint(columnNames = "account_details_id", name = "account_account_details_id_unique")
        }
)
public class Account extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_LOGIN = "account_login_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
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

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "account_account_access_level_fk"))
    private Set<AccountAccessLevel> accountAccessLevel = new HashSet<>();

    @NotNull
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_details_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "account_account_details_fk"))
    private AccountDetails accountDetails;

    @NotNull
    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "account", fetch = FetchType.LAZY,  orphanRemoval = true)
    private AccountAuthInfo accountAuthInfo;

    public Account() {
    }

    public Account(@NotNull @Size(min = 3, max = 30) String login, @NotNull @Size(min = 50, max = 60) String password,
                   @NotNull Boolean active, @NotNull Boolean confirm, Set<AccountAccessLevel> accountAccessLevel,
                   AccountDetails accountDetails, @NotNull AccountAuthInfo accountAuthInfo) {
        this.login = login;
        this.password = password;
        this.active = active;
        this.confirm = confirm;
        this.accountAccessLevel = accountAccessLevel;
        this.accountDetails = accountDetails;
        this.accountAuthInfo = accountAuthInfo;
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
        this.password = password;
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

    public AccountAuthInfo getAccountAuthInfo() {
        return accountAuthInfo;
    }

    public void setAccountAuthInfo(AccountAuthInfo accountAuthInfo) {
        this.accountAuthInfo = accountAuthInfo;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", active=" + active +
                ", confirm=" + confirm +
                '}';
    }
}
