package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import javax.validation.constraints.NotNull;

/**
 * Reprezentuje formularz zmiany statusu aktywności konta.
 */
public class AccountBlockDto {

    @NotNull
    private Boolean active;

    public AccountBlockDto() {
    }

    public AccountBlockDto(Account account) {
        this.active = account.getActive();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "AccountBlockDto{" +
                "active=" + active +
                '}';
    }
}
