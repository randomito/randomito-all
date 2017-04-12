/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.randomito.core.postprocessor.AnnotationInfo;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more:
 * {@link javax.validation.constraints.DecimalMin}
 * {@link javax.validation.constraints.DecimalMax}
 *
 * @author Miron Balcerzak, 2017
 */
public class DecimalMinMaxAnnotationPostProcessor extends BaseRangeAnnotationPostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(DecimalMin.class)
                && !ctx.isAnnotationPresent(DecimalMax.class)) {
            return value;
        }
        String minValue = "1";
        if (ctx.isAnnotationPresent(DecimalMin.class)) {
            minValue = ctx.getAnnotation(DecimalMin.class).value();
        }
        String maxValue = "50";
        if (ctx.isAnnotationPresent(DecimalMax.class)) {
            maxValue = ctx.getAnnotation(DecimalMax.class).value();
        }
        return range(minValue, maxValue, value.getClass());
    }
}
