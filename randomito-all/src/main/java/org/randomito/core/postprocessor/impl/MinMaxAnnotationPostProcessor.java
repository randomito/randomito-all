/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.randomito.core.postprocessor.AnnotationInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more:
 * {@link javax.validation.constraints.Min}
 * {@link javax.validation.constraints.Max}
 *
 * @author Miron Balcerzak, 2017
 */
public class MinMaxAnnotationPostProcessor extends BaseRangeAnnotationPostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Min.class)
                && !ctx.isAnnotationPresent(Max.class)) {
            return value;
        }
        long minValue = 1;
        if (ctx.isAnnotationPresent(Min.class)) {
            minValue = ctx.getAnnotation(Min.class).value();
        }
        long maxValue = 50;
        if (ctx.isAnnotationPresent(Max.class)) {
            maxValue = ctx.getAnnotation(Max.class).value();
        }
        if (Number.class.isAssignableFrom(value.getClass())) {
            return range(String.valueOf(minValue), String.valueOf(maxValue), value.getClass());
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() < minValue) {
                strVal += RandomStringUtils.randomAlphabetic((int) minValue - strVal.length());
            } else if (strVal.length() > maxValue) {
                strVal = strVal.substring(0, (int) maxValue);
            }
            return strVal;
        }
        return value;
    }

}