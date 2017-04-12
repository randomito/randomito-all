package org.randomito.entity;

import javax.inject.Inject;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Miron Balcerzak, 2017
 */
public class User extends BaseEntity {

    @Inject
    private InjectableService serviceByInject;

    @Size(min = 5, max = 30)
    private String login;

    private Address address;

    @NotNull
    private UserStatus status;

    @Size(min = 18, max = 90)
    @Digits(integer = 2, fraction = 0)
    @DecimalMin("18")
    @DecimalMax("90")
    private int age;

    @Pattern(regexp = "[A-Z0-9]+@[A-Z0-9]+\\.[A-Z]{2}")
    private String email;
    @Past
    private Date lastLogin;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}