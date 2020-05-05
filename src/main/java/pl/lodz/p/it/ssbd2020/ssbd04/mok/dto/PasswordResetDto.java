package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.VUUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordResetDto {
    @NotNull
    @Size(min = 1, max = 64)
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
