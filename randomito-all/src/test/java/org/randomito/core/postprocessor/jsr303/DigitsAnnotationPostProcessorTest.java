package org.randomito.core.postprocessor.jsr303;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Digits;

import static org.hamcrest.CoreMatchers.is;

public class DigitsAnnotationPostProcessorTest extends BasePostProcessorTest {

    public DigitsAnnotationPostProcessorTest() {
        super(new DigitsAnnotationPostProcessor());
    }

    @Test
    public void testIntegers() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Integer retVal = postprocess(wrapper, "anInt", Integer.class);

        // then
        Assert.assertTrue(retVal < 10);
    }

    @Test
    public void testFractions() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Double retVal = postprocess(wrapper, "aDouble", Double.class);

        // then
        Assert.assertTrue(retVal < 1);
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Float retVal = postprocess(wrapper, "aFloat", Float.class);

        // then
        Assert.assertThat(retVal, is(0f));
    }

    public static class Wrapper {

        @Digits(integer = 1, fraction = 0)
        private int anInt;

        @Digits(integer = 0, fraction = 2)
        private double aDouble;

        private float aFloat;
    }
}