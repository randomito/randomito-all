package org.randomito.core.generator.impl;

import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ArrayGeneratorTest extends BaseGenerationTest {

    private ArrayGenerator generation = new ArrayGenerator();

    @Test
    public void testCanHandle() {
        // given
        Class<String[]> aClass = String[].class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testCanHandle_false() {
        // given
        Class<ArrayGeneratorTest> aClass = ArrayGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }


    @Test
    public void testGenerate() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "array");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertThat(generated.getClass().isArray(), is(true));
        assertThat(((String[]) generated).length, is(3));
        assertThat(((String[]) generated)[0], is(nullValue()));
    }

    static class TestingClass {
        private String[] array;
    }
}