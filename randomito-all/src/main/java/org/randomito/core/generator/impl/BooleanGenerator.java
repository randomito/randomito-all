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

/**
 * Generator is handling boolean type values (boxed/unboxed).
 * <p>
 * See more: {@link java.lang.Boolean}
 *
 * @author Miron Balcerzak, 2017
 */
public class BooleanGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(final Class<?> clazz) {
        return Boolean.class == clazz || Boolean.TYPE == clazz;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return RandomUtils.nextBoolean();
    }
}
