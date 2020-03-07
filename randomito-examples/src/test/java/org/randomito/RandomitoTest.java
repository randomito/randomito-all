package org.randomito;

import org.hamcrest.number.OrderingComparison;
import org.junit.After;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.entity.Address;
import org.randomito.entity.User;

import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Simple use-cases of Randomito utilisation.
 *
 * @author Miron Balcerzak, 2017
 */
public class RandomitoTest {

    @After
    public void afterTest() throws IllegalAccessException {
        for (Field field : getClass().getDeclaredFields()) {
            field.set(this, null);
        }
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
    @Random
    @Random.SetValues({
            @Random.SetValue(property = "street", value = "Golden Street")
    })
    private Address test1_address;

    @Random
    private User test1_user;

    @Test
    public void test1_shouldBeNotNull() {
        // given
        Randomito.init(this);

        // then
        assertThat(test1_user, is(notNullValue()));
        assertThat(test1_user.getId(), is(nullValue()));
        assertThat(test1_user.getVersion(), is(nullValue()));
        assertThat(test1_user.getLogin(), is(notNullValue()));
        assertThat(test1_user.getAddress(), is(nullValue()));
        assertThat(test1_address, is(notNullValue()));
        assertThat(test1_address.getId(), is(nullValue()));
        assertThat(test1_address.getVersion(), is(nullValue()));
        assertThat(test1_address.getStreet(), is(notNullValue()));
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random
    @Random.SetValues({
            @Random.SetValue(property = "street", value = "Golden Street")
    })
    private Address test2_address;
    @Random(depth = 2)
    @Random.SetValues({
            @Random.SetValue(property = "address.street", value = "Brown Street"),
            @Random.SetValue(property = "login", value = Random.NULL)
    })
    private User test2_user;

    @Test
    public void test2_shouldInjectStaticValues() {
        // given
        Randomito.init(this);

        // then
        assertThat(test2_user, is(notNullValue()));
        assertThat(test2_user.getAddress(), is(notNullValue()));
        assertThat(test2_user.getLogin(), is(nullValue()));
        assertThat(test2_user.getAddress().getStreet(), is("Brown Street"));
        assertThat(test2_address, is(notNullValue()));
        assertThat(test2_address.getStreet(), is("Golden Street"));
        assertThat(test2_address, is(not(test2_user.getAddress())));
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random
    @Random.SetValue(property = "address", referenceBy = "test3_address")
    private User test3_user1;
    @Random
    @Random.SetValue(property = "address", referenceBy = "test3_address")
    private User test3_user2;
    @Random
    private Address test3_address;


    @Test
    public void test3_shouldInjectReferencedValue() {
        // given
        Randomito.init(this);

        // then
        assertThat(test3_user1.getAddress(), is(notNullValue()));
        assertThat(test3_user2.getAddress(), is(notNullValue()));
        assertThat(test3_address, is(notNullValue()));
        assertThat(test3_user1.getAddress(), is(test3_address));
        assertThat(test3_user2.getAddress(), is(test3_address));
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random
    @Random.SetValue(property = "street", value = "Golden Street")
    private Address test4_address;

    @Random(depth = 2)
    @Random.SetValue(property = "address.street", referenceBy = "test4_address.street")
    private User test4_user;

    @Test
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

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Test
    public void test5_shouldBeNotNull_and_1depth() {
        // when
        User user = Randomito.random(User.class, 1);

        // then
        assertThat(user, is(notNullValue()));
        assertThat(user.getAddress(), is(nullValue()));
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random
    private Map<Integer, User> test6_users;

    @Test
    public void test6_mapTest() {
        // when
        Randomito.init(this);

        // then
        assertThat(test6_users, is(notNullValue()));
        assertThat(test6_users.size(), is(3));
        for (User user : test6_users.values()) {
            assertThat(user.getLogin(), is(notNullValue()));
            assertThat(user.getStatus(), is(notNullValue()));
            assertThat(user.getAge(), is(notNullValue()));
            assertThat(user.getLastLogin(), is(notNullValue()));
            assertThat(user.getId(), is(nullValue()));
            assertThat(user.getVersion(), is(nullValue()));
        }
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random(depth = 2)
    private User test7_user;

    @Test
    public void test7_disableNullifyIdAndVersion() {
        // given
        Randomito.custom().disableNullifyIdAndVersion().init(this);

        // then
        assertThat(test7_user, is(notNullValue()));
        assertThat(test7_user.getId(), is(notNullValue()));
        assertThat(test7_user.getVersion(), is(notNullValue()));
        assertThat(test7_user.getLogin(), is(notNullValue()));
        assertThat(test7_user.getAddress(), is(notNullValue()));
        assertThat(test7_user.getAddress().getId(), is(notNullValue()));
        assertThat(test7_user.getAddress().getVersion(), is(notNullValue()));
    }

    // --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

    @Random
    private User test8_user;

    @Test
    public void test8_disableJsr303Validation() {
        // given
        Randomito.custom().disableJsr303().init(this);

        // then
        assertThat(test8_user, is(notNullValue()));
        // by default generated String is of length 150
        assertThat(test8_user.getLogin().length(), OrderingComparison.greaterThan(30));
    }

}
