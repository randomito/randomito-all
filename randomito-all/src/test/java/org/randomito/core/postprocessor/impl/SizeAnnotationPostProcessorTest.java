package org.randomito.core.postprocessor.impl;

import org.hamcrest.number.OrderingComparison;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Size;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Created by miciek on 07.04.2017.
 */
public class SizeAnnotationPostProcessorTest extends BasePostProcessorTest {


    public SizeAnnotationPostProcessorTest() {
        super(new SizeAnnotationPostProcessor());
    }

    @Test
    public void testSizeOnString() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.string1 = "some-string, some-string, some-string, some-string, some-string";

        // when
        String string1 = postprocess(wrapper, "string1", String.class);

        // then
        Assert.assertThat(string1.length(), OrderingComparison.lessThan(20));
    }

    @Test
    public void testSizeOnString_sillyValues() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.string3 = "some-string, some-string, some-string, some-string, some-string";

        // when
        String string3 = postprocess(wrapper, "string3", String.class);

        // then
        Assert.assertThat(string3.length(), OrderingComparison.lessThan(10000));
    }

    @Test
    public void testSizeOnInt() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Integer anInt = postprocess(wrapper, "anInt", Integer.class);

        // then
        Assert.assertThat(anInt, OrderingComparison.lessThan(20));
    }


    @Test
    public void testSizeOnUnhandledType() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object = new Object();

        // when
        Object obj = postprocess(wrapper, "object", Object.class);

        // then
        Assert.assertThat(obj, is(wrapper.object));
    }

    @Test
    public void noAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Integer string2 = postprocess(wrapper, "string2", Integer.class);

        // then
        Assert.assertThat(string2, is(nullValue()));
    }

    static class Wrapper {

        @Size(max = 20)
        private String string1;

        private String string2;

        @Size(min = -20, max = 654321)
        private String string3;

        @Size(max = 20)
        private int anInt;

        @Size
        private Object object;
    }
}