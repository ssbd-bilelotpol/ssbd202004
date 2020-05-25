package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountAccessLevelException;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reprezentuje zbiór poziomów dostępu z dodatkową funkcjonalnością konwersji nazwy poziomu dostępu na jego obiekt.
 */
public class AccountAccessLevelDto implements Signable {

    @NotNull
    @Size(min = 1, max = 4)
    private Set<String> accessLevels;

    private String login;

    private Long version;

    public AccountAccessLevelDto() {
    }

    public AccountAccessLevelDto(Account account) {
        this.accessLevels = account.getAccountAccessLevel().stream()
                .map(a -> a.getType()).collect(Collectors.toSet());
        this.login = account.getLogin();
        this.version = account.getVersion();
    }

    public Set<String> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<String> accessLevels) {
        this.accessLevels = accessLevels;
    }

    public Set<AccountAccessLevel> toAccountAccessLevelSet() throws AccountAccessLevelException {
        Set<AccountAccessLevel> accountAccessLevels = new HashSet<>();
        for (String role : accessLevels) {
            accountAccessLevels.add(this.toAccountAccessLevel(role));
        }
        return accountAccessLevels;
    }

    private AccountAccessLevel toAccountAccessLevel(String role) throws AccountAccessLevelException {
        if ("admin".equals(role)) {
            return new AdminAccessLevel();
        } else if ("client".equals(role)) {
            return new ClientAccessLevel();
        } else if ("manager".equals(role)) {
            return new ManagerAccessLevel();
        } else if ("customer_service".equals(role)) {
            return new CustomerServiceAccessLevel();
        }
        throw AccountAccessLevelException.notFound();
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s", version, login);
    }

    @Override
    public String toString() {
        return "AccountAccessLevelDto{" +
                "accessLevels=" + accessLevels +
                ", login='" + login + '\'' +
                ", version=" + version +
                '}';
    }
}
