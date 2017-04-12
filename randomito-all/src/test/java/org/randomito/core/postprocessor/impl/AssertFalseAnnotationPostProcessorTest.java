package org.randomito.core.postprocessor.impl;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertFalse;

public class AssertFalseAnnotationPostProcessorTest extends BasePostProcessorTest {


    public AssertFalseAnnotationPostProcessorTest() {
        super(new AssertFalseAnnotationPostProcessor());
    }

    @Test
    public void test_annotationPresent() throws Exception {
        // given
        Wrapper obj = new Wrapper();

        // when
        obj.bool1 = postprocess(obj, "bool1", Boolean.class);

        // then
        Assert.assertThat(obj.bool1, CoreMatchers.is(false));
    }

    @Test
    public void test_annotationNotFound() throws Exception {
        // given
        Wrapper obj = new Wrapper();

        // when
        obj.bool2 = postprocess(obj, "bool2", Boolean.class);
        ;

        // then
        Assert.assertThat(obj.bool2, CoreMatchers.is(true));
    }

    public class Wrapper {

        @AssertFalse
        private boolean bool1 = true;

        private boolean bool2 = true;

    }
}