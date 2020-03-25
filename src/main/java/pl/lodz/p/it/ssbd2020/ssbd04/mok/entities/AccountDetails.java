package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Klasa encyjna zawierająca informacje o szczegółach konta
 */
@Entity
@Table(name = "account_details")
public class AccountDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 255)
    @Email
    @Column(unique = true, updatable = false, nullable = false)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false)
    private String phoneNumber;

    @Version
    private Long version;

    public AccountDetails() {
    }

    public AccountDetails(@NotNull @Size(min = 1, max = 30) String firstName, @NotNull @Size(min = 1, max = 30) String lastName, @NotNull @Size(max = 255) @Email String email, @NotNull @Size(max = 50) String phoneNumber, Long version) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.version = version;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
