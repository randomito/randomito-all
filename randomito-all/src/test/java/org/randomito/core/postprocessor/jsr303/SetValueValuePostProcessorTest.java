package org.randomito.core.postprocessor.jsr303;

import org.junit.Assert;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.core.SetValueValuePostProcessor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class SetValueValuePostProcessorTest extends BasePostProcessorTest {

    public SetValueValuePostProcessorTest() {
        super(new SetValueValuePostProcessor());
    }

    @Test
    public void testSetValueAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object1 = new InnerClass();

        // when
        InnerClass object1 = postprocess(wrapper, "object1", InnerClass.class);

        // then
        Assert.assertThat(object1.value, is("String-value"));
    }

    @Test
    public void testValuesSetValueAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object2 = new InnerClass();

        // when
        InnerClass object2 = postprocess(wrapper, "object2", InnerClass.class);

        // then
        Assert.assertThat(object2.value, is("String-value"));
    }

    @Test
    public void testSetValueToNull() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object3 = new InnerClass();

        // when
        InnerClass object3 = postprocess(wrapper, "object3", InnerClass.class);

        // then
        Assert.assertThat(object3.value, is(nullValue()));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object4 = new InnerClass();

        // when
        InnerClass object4 = postprocess(wrapper, "object4", InnerClass.class);

        // then
        Assert.assertThat(object4.value, is("unchanged-in-test"));
    }

    @Test(expected = RandomitoException.class)
    public void testBothValueAndReferenceBy_exception() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object5 = new InnerClass();

        // when
        postprocess(wrapper, "object5", InnerClass.class);
    }

    @Test
    public void testReferenceByUnhandled() {
        // given
        Wrapper wrapper = new Wrapper();
        wrapper.object6 = new InnerClass();

        // when
        postprocess(wrapper, "object6", InnerClass.class);
    }


    static class Wrapper {

        @Random.SetValue(property = "value", value = "String-value")
        private InnerClass object1;

        @Random.SetValues({
                @Random.SetValue(property = "value", value = "String-value")
        })
        private InnerClass object2;

        @Random.SetValues({
                @Random.SetValue(property = "value", value = Random.NULL)
        })
        private InnerClass object3;

        private InnerClass object4;

        @Random.SetValue(property = "value", value = Random.NULL, referenceBy = "object3")
        private InnerClass object5;

        @Random.SetValue(property = "value", referenceBy = "object3")
        private InnerClass object6;

    }

    static class InnerClass {

        private String value = "unchanged-in-test";
    }
}