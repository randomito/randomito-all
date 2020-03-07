/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.jsr303;

import org.apache.commons.lang3.RandomStringUtils;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import javax.validation.constraints.Digits;
import java.util.Random;


/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more: {@link javax.validation.constraints.Digits}
 *
 * @author Miron Balcerzak, 2017
 */
public class DigitsAnnotationPostProcessor implements PostProcessor {

    private final static Random random = new Random();

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Digits.class)) {
            return value;
        }

        int integrals = ctx.getAnnotation(Digits.class).integer();
        String number = "0";
        if (integrals > 0) {
            number = RandomStringUtils.randomNumeric(integrals);
        }
        int fractions = ctx.getAnnotation(Digits.class).fraction();
        if (fractions > 0) {
            number += "." + RandomStringUtils.randomNumeric(fractions);
        }
        return ReflectionUtils.stringToNumber(number, value.getClass());
    }

}
