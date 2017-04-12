/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;

import java.lang.reflect.Array;

/**
 * Generator is handling plain-old array of specified type (generic type).
 *
 * @author Miron Balcerzak, 2017
 */
public class ArrayGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(Class<?> clazz) {
        return clazz.isArray();
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return Array.newInstance(ctx.getType().getComponentType(), 3);
    }
}
