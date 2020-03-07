/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.core;

import org.apache.commons.lang3.StringUtils;
import org.randomito.annotation.Random;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Injects "static" value into object.
 * <p>
 * See more:
 * {@link Random.SetValue}
 * {@link Random.SetValues}
 *
 * @author Miron Balcerzak, 2017
 */
public class SetValueValuePostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Random.SetValue.class)
                && !ctx.isAnnotationPresent(Random.SetValues.class)) {
            return value;
        }
        List<Random.SetValue> setValues = new ArrayList<>();
        if (ctx.isAnnotationPresent(Random.SetValues.class)) {
            Collections.addAll(setValues, ctx.getAnnotation(Random.SetValues.class).value());
        }
        if (ctx.isAnnotationPresent(Random.SetValue.class)) {
            Collections.addAll(setValues, ctx.getAnnotation(Random.SetValue.class));
        }
        List<Random.SetValue> values = setValues.stream()
                .filter(input -> StringUtils.isNotBlank(input.value()))
                .collect(Collectors.toList());
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
