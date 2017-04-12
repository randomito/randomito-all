/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.apache.commons.lang3.RandomUtils;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.postprocessor.PostProcessor;

import static java.lang.Integer.valueOf;

/**
 * Abstract class for all range operations.
 * <p>
 * See more:
 * {@link org.randomito.core.postprocessor.impl.DecimalMinMaxAnnotationPostProcessor}
 * {@link org.randomito.core.postprocessor.impl.MinMaxAnnotationPostProcessor}
 * {@link org.randomito.core.postprocessor.impl.SizeAnnotationPostProcessor}
 *
 * @author Miron Balcerzak, 2017
 */
abstract class BaseRangeAnnotationPostProcessor implements PostProcessor {

    protected <T> T range(String min, String max, Class<T> type) throws Exception {
        int randVal = RandomUtils.nextInt(valueOf(min), valueOf(max));
        return ReflectionUtils.stringToNumber(randVal, type);
    }
}
