/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

/**
 * Annotation scanner interface.
 * <p>
 * See more:
 * {@link org.randomito.core.AnnotationConfigScanner}
 * {@link org.randomito.core.scanner.AnnotationGenerationInfoScanner}
 * {@link org.randomito.core.scanner.AnnotationPostProcessorScanner}
 * {@link org.randomito.core.scanner.AnnotationTypeCreatorScanner}
 *
 * @author Miron Balcerzak, 2017
 */
public interface AnnotationScanner<T> {

    T scan(Object instance);
}
