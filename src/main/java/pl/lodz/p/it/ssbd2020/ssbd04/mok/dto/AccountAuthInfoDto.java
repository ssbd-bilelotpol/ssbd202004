package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import java.time.LocalDateTime;

/**
 * Reprezentuje informacje o ostatnim poprawnym uwierzytelnieniu
 */
public class AccountAuthInfoDto {

    private String login;

    private LocalDateTime lastSuccessAuth;

    private String lastIpAddress;


    public AccountAuthInfoDto() {
    }

    public AccountAuthInfoDto(String login, LocalDateTime lastSuccessAuth,
                              String lastIpAddress) {
        this.login = login;
        this.lastSuccessAuth = lastSuccessAuth;
        this.lastIpAddress = lastIpAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

}

