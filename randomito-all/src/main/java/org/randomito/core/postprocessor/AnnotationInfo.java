/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor;

import java.lang.annotation.Annotation;

/**
 * Provides available annotations in given context.
 * <p>
 * See more:
 * {@link org.randomito.core.DefaultContext}
 * {@link org.randomito.core.generator.TypeGenerator}
 *
 * @author Miron Balcerzak, 2017
 */
public interface AnnotationInfo {

    boolean isAnnotationPresent(Class<? extends Annotation> annotation);

    <T extends Annotation> T getAnnotation(Class<T> annotation);
}
