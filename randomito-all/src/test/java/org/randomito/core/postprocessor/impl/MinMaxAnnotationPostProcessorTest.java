package org.randomito.core.postprocessor.impl;

import org.hamcrest.CoreMatchers;
import org.hamcrest.number.OrderingComparison;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by miciek on 08.04.2017.
 */
public class MinMaxAnnotationPostProcessorTest extends BasePostProcessorTest {

    public MinMaxAnnotationPostProcessorTest() {
        super(new MinMaxAnnotationPostProcessor());
    }

    @Test
    public void testMinAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Integer min = postprocess(wrapper, "min", Integer.class);

        // then
        Assert.assertThat(min, OrderingComparison.greaterThanOrEqualTo(10));
    }

    @Test
    public void testMaxAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Long max = postprocess(wrapper, "max", Long.class);

        // then
        Assert.assertThat(max, OrderingComparison.lessThan(10l));
    }

    @Test
    public void testMinMaxAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Float minMax = postprocess(wrapper, "minMax", Float.class);

        // then
        Assert.assertThat(minMax, OrderingComparison.greaterThanOrEqualTo(5f));
        Assert.assertThat(minMax, OrderingComparison.lessThan(10f));
    }

    @Test
    public void testIgnored() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Integer minMax = postprocess(wrapper, "ignored", Integer.class);

        // then
        Assert.assertThat(minMax, CoreMatchers.is(0));
    }

    @Test
    public void testMinMaxOnStringAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.string = "abcdefghijklmon.......";

        // when
        String minMax = postprocess(wrapper, "string", String.class);

        // then
        Assert.assertThat(minMax.length(), OrderingComparison.greaterThan(2));
        Assert.assertThat(minMax.length(), OrderingComparison.lessThanOrEqualTo(5));
    }

    static class Wrapper {

        @Min(10)
        private int min;

        @Max(10)
        private long max;

        @Min(5)
        @Max(10)
        private float minMax;

        private int ignored;

        @Min(2)
        @Max(5)
        private String string;


    }
}