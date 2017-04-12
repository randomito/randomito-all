package org.randomito.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Miron Balcerzak, 2017
 */
public class Address extends BaseEntity {

    @Autowired
    private InjectableService serviceByInject;

    @Min(5)
    @Max(15)
    private String street;

    @Min(2)
    @Max(8)
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
