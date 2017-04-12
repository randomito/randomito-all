/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import org.randomito.annotation.Random;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.PostProcessor;

import java.lang.reflect.Field;

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
        return FluentIterable
                .from(ReflectionUtils.getDeclaredFields(instance.getClass(), true))
                .filter(new Predicate<Field>() {
                    @Override
                    public boolean apply(Field input) {
                        return input.isAnnotationPresent(Random.PostProcessor.class)
                                && PostProcessor.class.isAssignableFrom(input.getType());
                    }
                })
                .transform(new Function<Field, PostProcessor>() {
                    @Override
                    public PostProcessor apply(Field field) {
                        try {
                            if (!ReflectionUtils.makeFieldAccessible(field)) {
                                return null;
                            }
                            return (PostProcessor) field.get(instance);
                        } catch (IllegalAccessException e) {
                            throw new RandomitoException(e);
                        }
                    }
                })
                .filter(Predicates.<PostProcessor>notNull())
                .toArray(PostProcessor.class);
    }
}
