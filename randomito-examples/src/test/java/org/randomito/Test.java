package org.randomito;

import org.randomito.annotation.Random;
import org.randomito.entity.Address;
import org.randomito.entity.User;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by miciek on 12.04.2017.
 */
public class Test {

    @Random
    @Random.SetValue(property = "street", value = "Golden Street")
    private Address test4_address;

    @Random(depth = 2)
    @Random.SetValue(property = "address.street", referenceBy = "test4_address.street")
    private User test4_user;

    @org.junit.Test
    public void test4_shouldInject_byReferenceAndStatic_complex() {
        // given
        Randomito.init(this);
        // then
        assertThat(test4_user, is(notNullValue()));
        assertThat(test4_user.getAddress(), is(notNullValue()));
        assertThat(test4_address, is(notNullValue()));
        assertThat(test4_user.getAddress(), is(not(test4_address)));
        assertThat(test4_address.getStreet(), is("Golden Street"));
        assertThat(test4_user.getAddress().getStreet(), is("Golden Street"));
    }
}
