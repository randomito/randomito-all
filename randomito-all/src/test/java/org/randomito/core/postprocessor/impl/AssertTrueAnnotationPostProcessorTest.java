package org.randomito.core.postprocessor.impl;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

public class AssertTrueAnnotationPostProcessorTest extends BasePostProcessorTest {


    public AssertTrueAnnotationPostProcessorTest() {
        super(new AssertTrueAnnotationPostProcessor());
    }

    @Test
    public void test_annotationPresent() throws Exception {
        // given
        Wrapper obj = new Wrapper();

        // when
        obj.bool1 = postprocess(obj, "bool1", Boolean.class);

        // then
        Assert.assertThat(obj.bool1, CoreMatchers.is(true));
    }

    @Test
    public void test_annotationNotFound() throws Exception {
        // given
        Wrapper obj = new Wrapper();

        // when
        obj.bool2 = postprocess(obj, "bool2", Boolean.class);
        ;

        // then
        Assert.assertThat(obj.bool2, CoreMatchers.is(false));
    }

    public class Wrapper {

        @AssertTrue
        private boolean bool1 = false;

        private boolean bool2 = false;

    }

}