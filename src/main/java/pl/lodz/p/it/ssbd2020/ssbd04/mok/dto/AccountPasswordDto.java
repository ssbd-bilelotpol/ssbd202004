package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.Password;

import javax.validation.constraints.NotNull;

/**
 * Reprezentuje formularz zmiany hasła.
 */

public class AccountPasswordDto {

    @Password
    private String oldPassword;

    @NotNull
    @Password
    private String newPassword;

    private String captcha;


    public AccountPasswordDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "AccountPasswordDto{}";
    }
}
