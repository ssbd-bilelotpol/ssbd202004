package pl.lodz.p.it.ssbd2020.ssbd04.mok.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.FirstName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.LastName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.Phone;

import javax.validation.constraints.NotNull;

public class AccountEditDto {
    @NotNull
    @FirstName
    private String firstName;

    @NotNull
    @LastName
    private String lastName;

    @NotNull
    @Phone
    private String phoneNumber;

    private String captcha;

    public AccountEditDto() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "AccountEditDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
