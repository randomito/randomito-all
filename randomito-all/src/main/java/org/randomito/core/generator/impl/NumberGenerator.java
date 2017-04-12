/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.RandomUtils;
import org.randomito.core.DefaultContext;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;

/**
 * Handles all numbers (boxed/unboxed) and generates a value between 0 and 127.
 * <p>
 * See more:
 * {@link java.lang.Integer}
 * {@link java.lang.Long}
 * {@link java.lang.Byte}
 * {@link java.lang.Float}
 * {@link java.lang.Double}
 * {@link java.lang.Short}
 *
 * @author Miron Balcerzak, 2017
 */
public class NumberGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    @SuppressWarnings("unchecked")
    public boolean canHandle(final Class<?> clazz) {
        return FluentIterable.of(Integer.TYPE, Long.TYPE, Byte.TYPE, Float.TYPE, Double.TYPE, Short.TYPE,
                Integer.class, Long.class, Byte.class, Float.class, Double.class, Short.class)
                .anyMatch(new Predicate<Class<?>>() {
                    @Override
                    public boolean apply(Class<?> input) {
                        return input == clazz;
                    }
                });
    }


    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        Number number = Number.class.cast(RandomUtils.nextInt(0, 127));
        Class<?> type = ctx.getType();
        if (!type.isPrimitive()) {
            return ReflectionUtils.stringToNumber(number, type);
        }
        if (type == Integer.TYPE) {
            return number.intValue();
        } else if (type == Long.TYPE) {
            return number.longValue();
        } else if (type == Byte.TYPE) {
            return number.byteValue();
        } else if (type == Float.TYPE) {
            return number.floatValue();
        } else if (type == Double.TYPE) {
            return number.doubleValue();
        } else if (type == Short.TYPE) {
            return number.shortValue();
        }
        throw new RandomitoException("did not generate number value for type: " + type);
    }
}
