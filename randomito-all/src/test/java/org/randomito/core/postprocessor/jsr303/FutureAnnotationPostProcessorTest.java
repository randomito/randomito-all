package org.randomito.core.postprocessor.jsr303;

import org.hamcrest.number.OrderingComparison;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.Future;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Created by miciek on 07.04.2017.
 */
public class FutureAnnotationPostProcessorTest extends BasePostProcessorTest {

    public FutureAnnotationPostProcessorTest() {
        super(new FutureAnnotationPostProcessor());
    }

    @Test
    public void testFuture() {
        // given
        Wrapper wrapper = new Wrapper();

        Date future = postprocess(wrapper, "future", Date.class);

        Assert.assertThat(future, OrderingComparison.greaterThan(new Date()));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        Date date = postprocess(wrapper, "date", Date.class);

        Assert.assertThat(date, is(nullValue()));
    }

    static class Wrapper {

        @Future
        private Date future = new Date(0);

        private Date date;

    }
}