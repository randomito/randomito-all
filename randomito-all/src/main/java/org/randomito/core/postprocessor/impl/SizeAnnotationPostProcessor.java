/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.randomito.core.postprocessor.AnnotationInfo;

import javax.validation.constraints.Size;

/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more: {@link javax.validation.constraints.Size}
 *
 * @author Miron Balcerzak, 2017
 */
public class SizeAnnotationPostProcessor extends BaseRangeAnnotationPostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Size.class)) {
            return value;
        }
        long minValue = ctx.getAnnotation(Size.class).min();
        if(minValue < 0 ) {
            minValue = 0;
        }
        long maxValue = ctx.getAnnotation(Size.class).max();
        if( maxValue > 10000 ){
            maxValue = 10000;
        }
        if (Number.class.isAssignableFrom(value.getClass())) {
            return range(String.valueOf(minValue), String.valueOf(maxValue), value.getClass());
        } else if (value instanceof String) {
            return RandomStringUtils.randomAlphanumeric((int) minValue, (int) maxValue);
        }
        return value;
    }
}
