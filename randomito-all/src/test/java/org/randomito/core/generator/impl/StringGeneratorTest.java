package org.randomito.core.generator.impl;

import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class StringGeneratorTest extends BaseGenerationTest {

    private StringGenerator generation = new StringGenerator();

    @Test
    public void testCanHandle_false() {
        // given
        Class<?> aClass = StringGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }


    @Test
    public void testCanHandle() {
        // given
        Class<String> aClass = String.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testGenerate() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "object");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertThat(((String) generated).length(), is(150));
    }

    static class TestingClass {
        private String object;
    }


}