package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.FirstName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.LastName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.Phone;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

import static pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails.CONSTRAINT_EMAIL;

/**
 * Klasa encyjna zawierająca informacje o szczegółach konta
 */
@Entity
@Table(
        name = "account_details",
        uniqueConstraints = @UniqueConstraint(name = CONSTRAINT_EMAIL, columnNames = "email")
)
public class AccountDetails extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_EMAIL = "account_details_email_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_details_generator")
    @SequenceGenerator(name = "account_details_generator", sequenceName = "account_details_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30, name = "first_name")
    @FirstName
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30, name = "last_name")
    @LastName
    private String lastName;

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    @Column(updatable = false, nullable = false)
    private String email;

    @NotNull
    @Size(min = 9, max = 15)
    @Column(nullable = false, length = 15, name = "phone_number")
    @Phone
    private String phoneNumber;

    public AccountDetails() {
    }

    public AccountDetails(@NotNull @FirstName String firstName,
                          @NotNull @LastName String lastName, @NotNull @Size(min = 1, max = 255)
                          @Email String email, @NotNull @Phone String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDetails)) return false;

        AccountDetails that = (AccountDetails) o;

        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}
