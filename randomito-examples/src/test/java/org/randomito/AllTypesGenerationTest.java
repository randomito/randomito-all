package org.randomito;

import org.junit.Test;
import org.randomito.annotation.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * All available types aggregated within one test
 *
 * @author Miron Balcerzak, 2017
 */
public class AllTypesGenerationTest {

    private final static int DEPTH = 10_000;

    @Random(depth = DEPTH)
    private AllTypesObject object1;

    @Test
    public void test1_shouldHandleAllTypes() {
        // given
        object1 = null;

        // when
        Randomito.init(this);

        // then
        AllTypesObject object = object1;
        do {
            assertValues(object);
        }
        while ((object = object.inner) != null);
    }

    @Test
    public void test2_shouldHandleAllTypes() {
        // given
        object1 = null;

        // when
        object1 = Randomito.random(AllTypesObject.class, DEPTH);

        // then
        AllTypesObject object = object1;
        do {
            assertValues(object);
        }
        while ((object = object.inner) != null);
    }

    private void assertValues(AllTypesObject object) {
        assertThat(object, is(notNullValue()));
        assertThat(object.aByte, is(notNullValue()));
        assertThat(object.byteArrayPrimitive, is(notNullValue()));
        assertThat(object.byteArray, is(notNullValue()));
        assertThat(object.bytePrimitive, is(notNullValue()));
        assertThat(object.character, is(notNullValue()));
        assertThat(object.characterArray, is(notNullValue()));
        assertThat(object.charArrayPrimitive, is(notNullValue()));
        assertThat(object.charPrimitive, is(notNullValue()));
        assertThat(object.date, is(notNullValue()));
        assertThat(object.aDouble, is(notNullValue()));
        assertThat(object.doublePrimitive, is(notNullValue()));
        assertThat(object.anEnum, is(notNullValue()));
        assertThat(object.aFloat, is(notNullValue()));
        assertThat(object.floatPrimitive, is(notNullValue()));
        assertThat(object.integer, is(notNullValue()));
        assertThat(object.integerPrimitive, is(notNullValue()));
        assertThat(object.aLong, is(notNullValue()));
        assertThat(object.longPrimitive, is(notNullValue()));
        assertThat(object.aShort, is(notNullValue()));
        assertThat(object.date, is(notNullValue()));
        assertThat(object.listOfObjects, is(notNullValue()));
        assertThat(object.listOfStrings, is(notNullValue()));
        assertThat(object.arrayListOfStrings, is(notNullValue()));
        assertThat(object.setOfStrings, is(notNullValue()));
        assertThat(object.hashSetOfStrings, is(notNullValue()));
        assertThat(object.collectionOfObjects, is(notNullValue()));
        assertThat(object.integerStringMap, is(notNullValue()));
        assertThat(object.integerStringHashMap, is(notNullValue()));
    }

    public enum EnumExample {
        Example1, Example2, Example3, Example4
    }

    public static class AllTypesObject {

        private Byte aByte;
        private Byte[] byteArray;
        private byte[] byteArrayPrimitive;
        private byte bytePrimitive;
        private Character character;
        private Character[] characterArray;
        private char[] charArrayPrimitive;
        private char charPrimitive;
        private Date date;
        private Double aDouble;
        private double doublePrimitive;
        private EnumExample anEnum;
        private Float aFloat;
        private float floatPrimitive;
        private Integer integer;
        private int integerPrimitive;
        private Long aLong;
        private long longPrimitive;
        private Short aShort;
        private short shortPrimitive;
        private String string;
        private List listOfObjects;
        private List<String> listOfStrings;
        private ArrayList<String> arrayListOfStrings;
        private Set<String> setOfStrings;
        private HashSet<String> hashSetOfStrings;
        private Collection<Object> collectionOfObjects;
        private Map<Integer, String> integerStringMap;
        private HashMap<Integer, String> integerStringHashMap;

        // @Random(depth = ?), ? states how many recursive objects must be created
        private AllTypesObject inner;
    }

}
