package org.randomito.core.scanner;

import org.junit.Assert;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import static org.hamcrest.CoreMatchers.is;

public class AnnotationPostProcessorScannerTest {

    private final AnnotationPostProcessorScanner scanner = new AnnotationPostProcessorScanner();

    @Test
    public void testAnnotationNotFound() {
        // given
        NoAnnotation instance = new NoAnnotation();

        // when
        PostProcessor[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(0));
    }

    @Test
    public void testWithAnnotation() {
        // given
        WithAnnotation instance = new WithAnnotation();

        // when
        PostProcessor[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(1));
    }

    @Test
    public void testWithAnnotationOnWrongType() {
        // given
        WithAnnotationOnWrongType instance = new WithAnnotationOnWrongType();

        // when
        PostProcessor[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(0));
    }

    private static class NoAnnotation {

        private PostProcessor postProcessor;
    }

    private static class WithAnnotation {

        @Random.PostProcessor
        private PostProcessor postProcessor = new PostProcessor() {
            @Override
            public Object process(AnnotationInfo ctx, Object value) throws Exception {
                return null;
            }
        };
    }

    private static class WithAnnotationOnWrongType {

        @Random.PostProcessor
        private Object postProcessor = new Object();

    }

}