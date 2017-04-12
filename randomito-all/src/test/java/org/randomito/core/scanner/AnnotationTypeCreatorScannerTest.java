package org.randomito.core.scanner;

import org.junit.Assert;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.creator.TypeCreator;
import org.randomito.core.creator.TypeCreatorHolder;

import static org.hamcrest.CoreMatchers.is;

public class AnnotationTypeCreatorScannerTest {


    private final AnnotationTypeCreatorScanner scanner = new AnnotationTypeCreatorScanner();

    @Test
    public void testAnnotationNotFound() {
        // given
        NoAnnotation instance = new NoAnnotation();

        // when
        TypeCreatorHolder[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(0));
    }

    @Test
    public void testWithAnnotation() {
        // given
        WithAnnotation instance = new WithAnnotation();

        // when
        TypeCreatorHolder[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(1));
    }

    @Test
    public void testWithAnnotationOnWrongType() {
        // given
        WithAnnotationOnWrongType instance = new WithAnnotationOnWrongType();

        // when
        TypeCreatorHolder[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(0));
    }


    private static class NoAnnotation {

        private TypeCreator creator;
    }

    private static class WithAnnotation {

        @Random.TypeCreator(forType = Object.class)
        private TypeCreator creator = new TypeCreator() {
            @Override
            public Object newInstance() {
                return new Object();
            }
        };
    }

    private static class WithAnnotationOnWrongType {

        @Random.TypeCreator(forType = Object.class)
        private Object postProcessor = new Object();

    }

}