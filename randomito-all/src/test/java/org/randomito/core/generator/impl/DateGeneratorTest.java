package org.randomito.core.generator.impl;

import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DateGeneratorTest extends BaseGenerationTest {

    private DateGenerator generation = new DateGenerator();

    @Test
    public void testCanHandle_false() {
        // given
        Class<?> aClass = DateGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testCanHandle() {
        // given
        Class<?> aClass = Date.class;

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
        private Date date;
    }
}