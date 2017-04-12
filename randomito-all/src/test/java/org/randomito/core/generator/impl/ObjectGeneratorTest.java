package org.randomito.core.generator.impl;

import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ObjectGeneratorTest extends BaseGenerationTest {

    private ObjectGenerator generation = new ObjectGenerator();

    @Test
    public void testCanHandle_false() {
        // given
        Class<?> aClass = ObjectGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testCanHandle() {
        // given
        Class<?> aClass = Object.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testGenerate() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "date");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
    }

    static class TestingClass {
        private Object date;
    }

}