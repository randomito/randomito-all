package org.randomito.core.postprocessor.jsr303;

import org.hamcrest.number.OrderingComparison;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Past;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class PastAnnotationPostProcessorTest extends BasePostProcessorTest {

    public PastAnnotationPostProcessorTest() {
        super(new PastAnnotationPostProcessor());
    }

    @Test
    public void testPast() {
        // given
        Wrapper wrapper = new Wrapper();

        Date past = postprocess(wrapper, "past", Date.class);

        Assert.assertThat(past, OrderingComparison.lessThan(new Date()));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        Date date = postprocess(wrapper, "date", Date.class);

        Assert.assertThat(date, is(nullValue()));
    }

    static class Wrapper {

        @Past
        private Date past = new Date(Integer.MAX_VALUE);

        private Date date;

    }

}