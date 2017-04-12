package org.randomito.core;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AnnotationConfigScannerTest {

    @Test
    public void testAnnotationConfigScanner() {
        // given
        AnnotationConfigScanner annotationConfigScanner;

        // when
        annotationConfigScanner = new AnnotationConfigScanner();

        // then
        Assert.assertThat(annotationConfigScanner, is(notNullValue()));
    }

    @Test
    public void testAnnotationConfigScanner_scan() {
        // given
        AnnotationConfigScanner annotationConfigScanner = new AnnotationConfigScanner();

        // when
        AnnotationConfigScanner.Result scan = annotationConfigScanner.scan(this);

        // then
        Assert.assertThat(scan, is(notNullValue()));
    }

}