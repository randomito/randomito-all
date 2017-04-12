package org.randomito.core.postprocessor;

import org.junit.Test;
import org.randomito.core.postprocessor.impl.AssertTrueAnnotationPostProcessor;
import org.randomito.test.utils.TestUtils;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by miciek on 08.04.2017.
 */
public class PostProcessorExecutorTest {

    @Test
    public void testRegisterJsr303() {
        // given
        PostProcessorExecutor executor = spy(new PostProcessorExecutor());

        // when
        PostProcessorExecutor.registerJsr303(executor);

        // then
        verify(executor, times(9)).register(any(PostProcessor.class));
    }

    @Test
    public void testRegisterCore() {
        // given
        PostProcessorExecutor executor = spy(new PostProcessorExecutor());

        // when
        PostProcessorExecutor.registerCorePostProcessors(executor);

        // then
        verify(executor, times(2)).register(any(PostProcessor.class));
    }

    @Test
    public void testPostProcess() throws Exception {
        // given
        PostProcessorExecutor executor = spy(new PostProcessorExecutor());
        executor.register(new AssertTrueAnnotationPostProcessor());
        Wrapper instance = new Wrapper();

        // when
        executor.postprocess(TestUtils.createCtx(instance, "isTrue"));

        // then
        assertTrue(instance.isTrue);
    }

    @Test
    public void testPostProcess_registerUnregister() throws Exception {
        // given
        PostProcessorExecutor executor = spy(new PostProcessorExecutor());
        executor.register(new AssertTrueAnnotationPostProcessor());
        executor.unregister(AssertTrueAnnotationPostProcessor.class);
        Wrapper instance = new Wrapper();

        // when
        executor.postprocess(TestUtils.createCtx(instance, "isTrue"));

        // then
        assertFalse(instance.isTrue);
    }


    static class Wrapper {

        @AssertTrue
        private boolean isTrue = false;

    }

}