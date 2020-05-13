package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Klasa encyjna zawierająca informacje o historii uwierzytelnienia
 */
@NamedQueries({
        @NamedQuery(name = "AccountAuthInfo.findByAccountId",
                query = "SELECT account_auth_info FROM AccountAuthInfo account_auth_info JOIN account_auth_info.account acc WHERE acc.id = :id")
})
@Entity
@Table(
        name = "account_auth_info",
        indexes = {@Index(name = "account_auth_info_account_fk", columnList = "account_id")},
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "account_id", name = "account_auth_info_account_id_unique")
        }
)
public class AccountAuthInfo extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "current_auth")
    private LocalDateTime currentAuth;

    @Column(name = "last_success_auth")
    private LocalDateTime lastSuccessAuth;

    @Column(name = "last_incorrect_auth")
    private LocalDateTime lastIncorrectAuth;

    @Column(name = "last_ip_address", length = 45)
    private String lastIpAddress;

    @Column(name = "incorrect_auth_count", nullable = false)
    private Integer incorrectAuthCount;

    @NotNull
    @OneToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "account_id", nullable = false, updatable = false, unique = true,
            foreignKey = @ForeignKey(name = "account_auth_info_account_fk"))
    private Account account;


    public AccountAuthInfo() {
    }

    public AccountAuthInfo(@NotNull Account account, Integer incorrectAuthCount) {
        this.account = account;
        this.incorrectAuthCount = incorrectAuthCount;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLastSuccessAuth() {
        return lastSuccessAuth;
    }

    public void setLastSuccessAuth(LocalDateTime lastSuccessAuth) {
        this.lastSuccessAuth = lastSuccessAuth;
    }

    public String getLastIpAddress() {
        return lastIpAddress;
    }

    public void setLastIpAddress(String lastIpAddress) {
        this.lastIpAddress = lastIpAddress;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getLastIncorrectAuth() {
        return lastIncorrectAuth;
    }

    public void setLastIncorrectAuth(LocalDateTime lastIncorrectAuth) {
        this.lastIncorrectAuth = lastIncorrectAuth;
    }

    public LocalDateTime getCurrentAuth() {
        return currentAuth;
    }

    public void setCurrentAuth(LocalDateTime currentAuth) {
        this.currentAuth = currentAuth;
    }

    public Integer getIncorrectAuthCount() {
        return incorrectAuthCount;
    }

    public void setIncorrectAuthCount(Integer incorrectAuthCount) {
        this.incorrectAuthCount = incorrectAuthCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountAuthInfo)) return false;

        AccountAuthInfo that = (AccountAuthInfo) o;

        return getAccount().equals(that.getAccount());
    }

    @Override
    public int hashCode() {
        return getAccount().hashCode();
    }

    @Override
    public String toString() {
        return "AccountAuthInfo{" +
                "id=" + id +
                ", version=" + getVersion() +
                '}';
    }
}
