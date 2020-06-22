package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.FirstName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.LastName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reprezentuje żądanie stworzenia pasażera
 */
public class CreatePassengerDto {

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    private String email;

    @NotNull
    @FirstName
    private String firstName;

    @NotNull
    @LastName
    private String lastName;

    @NotNull
    @Phone
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
