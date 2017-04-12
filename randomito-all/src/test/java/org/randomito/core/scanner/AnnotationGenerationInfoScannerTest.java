package org.randomito.core.scanner;

import org.junit.Assert;
import org.junit.Test;
import org.randomito.annotation.Random;
import org.randomito.core.GenerationInfo;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by miciek on 08.04.2017.
 */
public class AnnotationGenerationInfoScannerTest {

    private final AnnotationGenerationInfoScanner scanner = new AnnotationGenerationInfoScanner();

    @Test
    public void testAnnotationNotFound() {
        // given
        NoAnnotation instance = new NoAnnotation();

        // when
        GenerationInfo[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(0));
    }

    @Test
    public void testWithAnnotation() {
        // given
        WithAnnotation instance = new WithAnnotation();

        // when
        GenerationInfo[] scan = scanner.scan(instance);

        // then
        Assert.assertThat(scan.length, is(1));
        Assert.assertThat(scan[0].getDepth(), is(1));
        Assert.assertThat(scan[0].getName(), is("anInt"));
        Assert.assertThat(scan[0].isInheritanceAllowed(), is(true));
        Assert.assertThat(scan[0].isSkip(), is(false));
    }

    private static class NoAnnotation {

        private int anInt;
    }

    private static class WithAnnotation {

        @Random
        private int anInt;
    }
}