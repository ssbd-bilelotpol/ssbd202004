package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.Password;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.VUUID;

import javax.validation.constraints.NotNull;

public class PasswordResetDto {
    @NotNull
    @Password
    String password;
    @NotNull
    @VUUID
    String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PasswordResetDto{" +
                "token='" + token + '\'' +
                '}';
    }
}
