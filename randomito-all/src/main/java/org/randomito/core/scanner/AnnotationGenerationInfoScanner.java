/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

import org.randomito.annotation.Random;
import org.randomito.core.GenerationInfo;
import org.randomito.core.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Scans for @Random annotations, creates upon research instance of GenerationInfo so it
 * is used later in scope of DefaultContext instance.
 * <p>
 * See more:
 * {@link Random}
 * {@link org.randomito.core.AnnotationConfigScanner}
 * {@link org.randomito.core.DefaultContext}
 *
 * @author Miron Balcerzak, 2017
 */
public class AnnotationGenerationInfoScanner implements AnnotationScanner<GenerationInfo[]> {

    @Override
    public GenerationInfo[] scan(final Object instance) {
        return Arrays.stream(ReflectionUtils.getDeclaredFields(instance.getClass(), true))
                .filter(input -> input.isAnnotationPresent(Random.class))
                .map(this::createConfiguration)
                .filter(input -> !input.isSkip())
                .toArray(GenerationInfo[]::new);
    }

    private GenerationInfo createConfiguration(Field field) {
        Random anno = field.getAnnotation(Random.class);
        return new GenerationInfo(field.getName(), anno.depth(), anno.inheritanceAllowed(), anno.skip());
    }

}
