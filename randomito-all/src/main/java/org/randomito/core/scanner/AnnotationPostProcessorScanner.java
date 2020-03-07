/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

import org.randomito.annotation.Random;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.PostProcessor;

import java.util.Arrays;
import java.util.Objects;

/**
 * Scans for @Random.PostProcessor annotations.
 * <p>
 * See more:
 * {@link org.randomito.annotation.Random.PostProcessor}
 * {@link org.randomito.core.AnnotationConfigScanner}
 * {@link org.randomito.core.postprocessor.PostProcessorExecutor}
 *
 * @author Miron Balcerzak, 2017
 */
public class AnnotationPostProcessorScanner implements AnnotationScanner<PostProcessor[]> {

    public PostProcessor[] scan(final Object instance) {
        return Arrays.stream(ReflectionUtils.getDeclaredFields(instance.getClass(), true))
                .filter(input -> input.isAnnotationPresent(Random.PostProcessor.class) && PostProcessor.class.isAssignableFrom(input.getType()))
                .map(field -> {
                    try {
                        if (!ReflectionUtils.makeFieldAccessible(field)) {
                            return null;
                        }
                        return (PostProcessor) field.get(instance);
                    } catch (IllegalAccessException e) {
                        throw new RandomitoException(e);
                    }
                })
                .filter(Objects::nonNull)
                .toArray(PostProcessor[]::new);
    }
}
