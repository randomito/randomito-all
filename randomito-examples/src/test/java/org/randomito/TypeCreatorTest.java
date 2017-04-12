package org.randomito;

import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.creator.TypeCreator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Type creator use-case test
 *
 * @author Miron Balcerzak, 2017
 */
public class TypeCreatorTest {

    @Random.TypeCreator(forType = ObjectWithNoDefaultConstr.class)
    private TypeCreator creator = new TypeCreator() {
        @Override
        public Object newInstance() {
            return new ObjectWithNoDefaultConstr("dummy1", false);
        }
    };

    @Random
    private ObjectWithNoDefaultConstr object;

    @Test
    public void shouldCreateAndRandomizeObject() {
        // given
        Randomito.init(this);
        // then
        assertThat(object, is(notNullValue()));
        assertThat(object.str, is("dummy1"));
        assertThat(object.bool, is(false));
        assertThat(object.intValue, is(notNullValue()));
        assertThat(object.str2, is(notNullValue()));
    }

    public static class ObjectWithNoDefaultConstr {

        private final Boolean bool;
        private final String str;
        private Integer intValue;
        private String str2;

        public ObjectWithNoDefaultConstr(String str1, Boolean bool) {
            this.str = str1;
            this.bool = bool;
        }

    }
}
