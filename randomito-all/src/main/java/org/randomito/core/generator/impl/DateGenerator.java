/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.apache.commons.lang3.RandomUtils;
import org.randomito.core.DefaultContext;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;

import java.util.Date;

/**
 * Handles date type object.
 * <p>
 * See more: {@link java.util.Date}
 *
 * @author Miron Balcerzak, 2017
 */
public class DateGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(Class<?> clazz) {
        return Date.class == clazz;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return new Date(RandomUtils.nextLong() % System.currentTimeMillis());
    }
}
