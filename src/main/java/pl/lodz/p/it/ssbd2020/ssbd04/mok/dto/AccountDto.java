package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDto implements Signable {
    @NotNull
    @Size(min = 3, max = 30)
    private String login;

    @NotNull
    @Size(min = 1, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 9, max = 15)
    private String phoneNumber;

    private Long version;
    private Long detailsVersion;

    public AccountDto() {
    }

    public AccountDto(Account account) {
        this.version = account.getVersion();
        this.detailsVersion = account.getAccountDetails().getVersion();
        this.login = account.getLogin();
        this.firstName = account.getAccountDetails().getFirstName();
        this.lastName = account.getAccountDetails().getLastName();
        this.email = account.getAccountDetails().getEmail();
        this.phoneNumber = account.getAccountDetails().getPhoneNumber();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%d.%s", version, detailsVersion, login);
    }
}
