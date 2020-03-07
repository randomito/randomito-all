/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

import org.randomito.annotation.Random;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.creator.TypeCreator;
import org.randomito.core.creator.TypeCreatorHolder;
import org.randomito.core.exception.RandomitoException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Scans for @Random.TypeCreator annotations.
 * <p>
 * See more:
 * {@link org.randomito.annotation.Random.TypeCreator}
 * {@link org.randomito.core.AnnotationConfigScanner}
 * {@link org.randomito.core.generator.impl.CollectionGenerator}
 * {@link org.randomito.core.generator.impl.ComplexTypeGenerator}
 * {@link org.randomito.core.generator.impl.MapGenerator}
 *
 * @author Miron Balcerzak, 2017
 */
public class AnnotationTypeCreatorScanner implements AnnotationScanner<TypeCreatorHolder[]> {

    @Override
    public TypeCreatorHolder[] scan(final Object instance) {
        return Arrays.stream(ReflectionUtils.getDeclaredFields(instance.getClass(), true))
                .filter(input -> input.isAnnotationPresent(Random.TypeCreator.class)
                        && TypeCreator.class.isAssignableFrom(input.getType()))
                .map(field -> {
                    try {
                        if (!ReflectionUtils.makeFieldAccessible(field)) {
                            return null;
                        }
                        TypeCreator typeCreator = (TypeCreator) field.get(instance);
                        Class<?> forType = field.getAnnotation(Random.TypeCreator.class).forType();
                        return new TypeCreatorHolder(forType, typeCreator);
                    } catch (IllegalAccessException e) {
                        throw new RandomitoException(e);
                    }
                })
                .filter(Objects::nonNull)
                .toArray(TypeCreatorHolder[]::new);
    }
}
