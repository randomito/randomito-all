/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.randomito.annotation.Random;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import java.util.Collections;
import java.util.List;

/**
 * Injects "static" value into object.
 * <p>
 * See more:
 * {@link org.randomito.annotation.Random.SetValue}
 * {@link org.randomito.annotation.Random.Values}
 *
 * @author Miron Balcerzak, 2017
 */
public class StaticValuePostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Random.SetValue.class)
                && !ctx.isAnnotationPresent(Random.Values.class)) {
            return value;
        }
        List<Random.SetValue> setValues = Lists.newArrayList();
        if (ctx.isAnnotationPresent(Random.Values.class)) {
            Collections.addAll(setValues, ctx.getAnnotation(Random.Values.class).value());
        }
        if (ctx.isAnnotationPresent(Random.SetValue.class)) {
            Collections.addAll(setValues, ctx.getAnnotation(Random.SetValue.class));
        }
        ImmutableList<Random.SetValue> values = FluentIterable.from(setValues)
                .filter(new Predicate<Random.SetValue>() {
                    @Override
                    public boolean apply(Random.SetValue input) {
                        return !Strings.isNullOrEmpty(input.value());
                    }
                }).toList();
        for (Random.SetValue inject : values) {
            String pathName = inject.property();
            String staticValue = inject.value();
            if (StringUtils.isNotBlank(inject.referenceBy())) {
                throw new RandomitoException("Only one setting can be specified (referenceBy or value)");
            }
            if (Random.NULL.equals(staticValue)) {
                staticValue = null;
            }
            ReflectionUtils.setPathValue(value, pathName, staticValue);
        }
        return value;
    }
}
