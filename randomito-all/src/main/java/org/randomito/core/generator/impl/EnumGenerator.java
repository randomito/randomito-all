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
 * Handles enumeration type object.
 * <p>
 * See more: {@link java.lang.Enum}
 *
 * @author Miron Balcerzak, 2017
 */
public class EnumGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        Object[] enums = ctx.getField().getType().getEnumConstants();
        return enums[RandomUtils.nextInt(0, enums.length)];
    }
}
