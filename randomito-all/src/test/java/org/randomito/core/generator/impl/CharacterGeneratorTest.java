package org.randomito.core.generator.impl;

import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CharacterGeneratorTest extends BaseGenerationTest {

    private CharacterGenerator generation = new CharacterGenerator();

    @Test
    public void testCanHandle_false() {
        // given
        Class<?> aClass = CharacterGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testCanHandle_primitive() {
        // given
        Class<?> aClass = char.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testCanHandle_wrapper() {
        // given
        Class<?> aClass = Character.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testGenerate_primitive() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "primitive");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
    }

    @Test
    public void testGenerate_wrapper() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "wrapper");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
    }

    static class TestingClass {
        private char primitive;
        private Character wrapper;
    }
}