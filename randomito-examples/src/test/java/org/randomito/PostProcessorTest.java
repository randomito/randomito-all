package org.randomito;

import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;
import org.randomito.entity.Address;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Custom PostProcessor usage example.
 *
 * @author Miron Balcerzak, 2017
 */
public class PostProcessorTest {

    private boolean postProcessorAccessed = false;

    @Random.PostProcessor
    private PostProcessor postProcessor = new PostProcessor() {
        @Override
        public Object process(AnnotationInfo ctx, Object value) throws Exception {
            postProcessorAccessed = true;
            return value;
        }
    };

    @Random
    private Address test4_address;

    @Test
    public void shouldExecuteCustomPostProcessor() {
        // given
        Randomito.init(this);
        // then
        assertThat(test4_address, is(notNullValue()));
        assertThat(postProcessorAccessed, is(true));
    }
}
