package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.FirstName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.LastName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.Phone;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PassengerDto implements Signable {

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

    private Long version;

    @Override
    public String createMessage() {
        return String.format("%s.%s", this.email, this.version);
    }

}
