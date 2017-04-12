/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.scanner;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.randomito.annotation.Random;
import org.randomito.core.GenerationInfo;
import org.randomito.core.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Scans for @Random annotations, creates upon research instance of GenerationInfo so it
 * is used later in scope of DefaultContext instance.
 * <p>
 * See more:
 * {@link org.randomito.annotation.Random}
 * {@link org.randomito.core.AnnotationConfigScanner}
 * {@link org.randomito.core.DefaultContext}
 *
 * @author Miron Balcerzak, 2017
 */
public class AnnotationGenerationInfoScanner implements AnnotationScanner<GenerationInfo[]> {

    @Override
    public GenerationInfo[] scan(final Object instance) {
        return FluentIterable
                .from(ReflectionUtils.getDeclaredFields(instance.getClass(), true))
                .filter(new Predicate<Field>() {
                    @Override
                    public boolean apply(Field input) {
                        return input.isAnnotationPresent(Random.class);
                    }
                })
                .transform(new Function<Field, GenerationInfo>() {
                    @Override
                    public GenerationInfo apply(Field input) {
                        return createConfiguration(input);
                    }
                })
                .filter(new Predicate<GenerationInfo>() {
                    @Override
                    public boolean apply(GenerationInfo input) {
                        return !input.isSkip();
                    }
                }).toArray(GenerationInfo.class);
    }

    private GenerationInfo createConfiguration(Field field) {
        Random anno = field.getAnnotation(Random.class);
        return new GenerationInfo(field.getName(), anno.depth(), anno.inheritanceAllowed(), anno.skip());
    }

}
