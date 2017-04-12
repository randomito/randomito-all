/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.randomito.core.DefaultContext;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;

/**
 * Handles string type - generates alphabetic values of length 250.
 * <p>
 * See more: {@link java.lang.String}
 *
 * @author Miron Balcerzak, 2017
 */
public class StringGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(Class<?> clazz) {
        return String.class == clazz;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return RandomStringUtils.randomAlphabetic(150);
    }
}
