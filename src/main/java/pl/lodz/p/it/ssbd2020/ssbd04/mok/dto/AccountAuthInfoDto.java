package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import java.time.LocalDateTime;

/**
 * Reprezentuje informacje o ostatnim poprawnym uwierzytelnieniu
 */
public class AccountAuthInfoDto {
    private String login;

    private LocalDateTime lastSuccessAuth;

    private LocalDateTime lastIncorrectAuth;

    private LocalDateTime currentAuth;

    private String lastIpAddress;

    public AccountAuthInfoDto() {
    }

    public AccountAuthInfoDto(String login, LocalDateTime currentAuth, String lastIpAddress) {
        this.login = login;
        this.currentAuth = currentAuth;
        this.lastIpAddress = lastIpAddress;
    }

    public AccountAuthInfoDto(LocalDateTime lastSuccessAuth, LocalDateTime lastIncorrectAuth) {
        this.lastSuccessAuth = lastSuccessAuth;
        this.lastIncorrectAuth = lastIncorrectAuth;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getLastSuccessAuth() {
        return lastSuccessAuth;
    }

    public String getLastIpAddress() {
        return lastIpAddress;
    }

    public LocalDateTime getLastIncorrectAuth() {
        return lastIncorrectAuth;
    }

    public LocalDateTime getCurrentAuth() {
        return currentAuth;
    }


    @Override
    public String toString() {
        return "AccountAuthInfoDto{" +
                "login='" + login + '\'' +
                ", lastSuccessAuth=" + currentAuth +
                ", lastSuccessAuth=" + lastSuccessAuth +
                ", lastSuccessAuth=" + lastIncorrectAuth +
                ", lastIpAddress='" + lastIpAddress + '\'' +
                '}';
    }

}

