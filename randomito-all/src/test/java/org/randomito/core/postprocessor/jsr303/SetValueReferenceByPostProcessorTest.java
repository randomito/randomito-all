package org.randomito.core.postprocessor.jsr303;

import org.junit.Assert;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.core.SetValueReferenceByPostProcessor;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by miciek on 07.04.2017.
 */
public class SetValueReferenceByPostProcessorTest extends BasePostProcessorTest {

    public SetValueReferenceByPostProcessorTest() {
        super(new SetValueReferenceByPostProcessor());
    }

    @Test
    public void testSetValueAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object1 = new InnerClass();

        // when
        InnerClass object1 = postprocess(wrapper, "object1", InnerClass.class);

        // then
        Assert.assertThat(object1.value, is("set-in-test"));
    }

    @Test
    public void testValuesSetValueAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object2 = new InnerClass();

        // when
        InnerClass object2 = postprocess(wrapper, "object2", InnerClass.class);

        // then
        Assert.assertThat(object2.value, is("set-in-test"));
    }

    @Test(expected = RandomitoException.class)
    public void testBothValueAndReferenceBy_exception() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object3 = new InnerClass();

        // when
        postprocess(wrapper, "object3", InnerClass.class);
    }

    @Test
    public void testValuePropertyUnhandled() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object4 = new InnerClass();

        // when
        postprocess(wrapper, "object4", InnerClass.class);
    }


    static class Wrapper {

        private String referenceByString = "set-in-test";

        @Random.SetValue(property = "value", referenceBy = "referenceByString")
        private InnerClass object1;

        @Random.SetValues({
                @Random.SetValue(property = "value", referenceBy = "referenceByString")
        })
        private InnerClass object2;

        @Random.SetValue(property = "value", value = Random.NULL, referenceBy = "object3")
        private InnerClass object3;

        @Random.SetValue(property = "value", value = "set-by-static")
        private InnerClass object4;

        private InnerClass object5;
    }

    static class InnerClass {

        private String value = "unchanged-in-test";
    }

}