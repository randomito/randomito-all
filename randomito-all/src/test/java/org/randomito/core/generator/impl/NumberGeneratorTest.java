package org.randomito.core.generator.impl;

import org.junit.Before;
import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NumberGeneratorTest extends BaseGenerationTest {

    private NumberGenerator generator;

    @Before
    public void init() {
        generator = init(new NumberGenerator());
    }

    @Test
    public void testCanHandle() {
        assertTrue(generator.canHandle(Integer.TYPE));
        assertTrue(generator.canHandle(Long.TYPE));
        assertTrue(generator.canHandle(Byte.TYPE));
        assertTrue(generator.canHandle(Float.TYPE));
        assertTrue(generator.canHandle(Double.TYPE));
        assertTrue(generator.canHandle(Short.TYPE));
        assertTrue(generator.canHandle(Integer.class));
        assertTrue(generator.canHandle(Long.class));
        assertTrue(generator.canHandle(Byte.class));
        assertTrue(generator.canHandle(Float.class));
        assertTrue(generator.canHandle(Double.class));
        assertTrue(generator.canHandle(Short.class));
    }

    @Test
    public void testCanHandle_false() {
        // given
        Class<NumberGeneratorTest> aClass = NumberGeneratorTest.class;

        // when
        boolean retVal = generator.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testGenerate_int() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aint");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Integer.class);
    }

    @Test
    public void testGenerate_long() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "along");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Long.class);
    }

    @Test
    public void testGenerate_byte() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "abyte");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Byte.class);
    }

    @Test
    public void testGenerate_float() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "afloat");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Float.class);
    }

    @Test
    public void testGenerate_double() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "adouble");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Double.class);
    }

    @Test
    public void testGenerate_short() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "ashort");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == Short.class);
    }

    @Test
    public void testGenerate_Integer() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aInteger");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }

    @Test
    public void testGenerate_Long() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aLong");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }

    @Test
    public void testGenerate_Byte() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aByte");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }

    @Test
    public void testGenerate_Float() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aFloat");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }

    @Test
    public void testGenerate_Double() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aDouble");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }

    @Test
    public void testGenerate_Short() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "aShort");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(generated.getClass() == ctx.getType());
    }


    static class TestingClass {
        private int aint;
        private long along;
        private byte abyte;
        private float afloat;
        private double adouble;
        private short ashort;
        private Integer aInteger;
        private Long aLong;
        private Byte aByte;
        private Float aFloat;
        private Double aDouble;
        private Short aShort;
    }

}