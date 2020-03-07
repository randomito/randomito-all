package org.randomito.core.postprocessor.jsr303;

import org.junit.Test;

import javax.validation.constraints.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PatternAnnotationPostProcessorTest extends BasePostProcessorTest {

    public PatternAnnotationPostProcessorTest() {
        super(new PatternAnnotationPostProcessor());
    }

    @Test
    public void testPatternAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        String string1 = postprocess(wrapper, "string1", String.class);

        // then
        assertThat(string1, is(string1));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        String string2 = postprocess(wrapper, "string2", String.class);

        // then
        assertThat(string2, is(string2));
    }

    static class Wrapper {

        @Pattern(regexp = "fix-me")
        private String string1;

        private String string2;

    }
}