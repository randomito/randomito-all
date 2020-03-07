/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.core;

import org.apache.commons.lang3.StringUtils;
import org.randomito.annotation.Random;
import org.randomito.core.DefaultContext;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Injects reference of the object into field of other object.
 * <p>
 * See more:
 * {@link Random}
 * {@link Random.SetValues}
 * {@link Random.SetValue}
 *
 * @author Miron Balcerzak, 2017
 */
public class SetValueReferenceByPostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Random.SetValue.class)
                && !ctx.isAnnotationPresent(Random.SetValues.class)) {
            return value;
        }
        List<Random.SetValue> setValues = new ArrayList<>();
        if (ctx.isAnnotationPresent(Random.SetValues.class)) {
            Random.SetValue[] value1 = ctx.getAnnotation(Random.SetValues.class).value();
            Collections.addAll(setValues, value1);
        }
        if (ctx.isAnnotationPresent(Random.SetValue.class)) {
            Collections.addAll(setValues, ctx.getAnnotation(Random.SetValue.class));
        }
        List<Random.SetValue> values = setValues.stream()
                .filter(input -> StringUtils.isNotBlank(input.referenceBy()))
                .collect(Collectors.toList());
        for (Random.SetValue inject : values) {
            String refValue = inject.referenceBy();
            if (StringUtils.isNotBlank(inject.value())) {
                throw new RandomitoException("Only one setting can be specified (referenceBy or value)");
            }
            if (StringUtils.isNotBlank(refValue)) {
                String pathName = inject.property();
                Object pathValue = ReflectionUtils.getPathValue(((DefaultContext) ctx).getRef(), refValue);
                ReflectionUtils.setPathValue(value, pathName, pathValue);
            }
        }
        return value;
    }
}
