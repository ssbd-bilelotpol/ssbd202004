package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reprezentuje formularz zmiany hasła.
 */

public class AccountPasswordDto {

    @Size(min = 8, max = 64)
    private String oldPassword;

    @NotNull
    @Size(min = 8, max = 64)
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
