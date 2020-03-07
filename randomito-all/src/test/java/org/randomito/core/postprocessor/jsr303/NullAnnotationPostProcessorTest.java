package org.randomito.core.postprocessor.jsr303;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Null;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Created by miciek on 07.04.2017.
 */
public class NullAnnotationPostProcessorTest extends BasePostProcessorTest {

    public NullAnnotationPostProcessorTest() {
        super(new NullAnnotationPostProcessor());
    }

    @Test
    public void testNull() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        String value1 = postprocess(wrapper, "value1", String.class);

        // then
        Assert.assertThat(value1, is(nullValue()));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        String value2 = postprocess(wrapper, "value2", String.class);

        // then
        Assert.assertThat(value2, is(notNullValue()));
    }

    static class Wrapper {

        @Null
        private String value1 = "string";

        private String value2 = "string";

    }
}