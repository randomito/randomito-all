/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;


/**
 * Handles raw object type. Used in case of interfaces with generic values without specified generic type.
 * <p>
 * See more: {@link java.lang.Object}
 *
 * @author Miron Balcerzak, 2017
 */
public class ObjectGenerator implements TypeGenerator, TypeGenerationPredicate {

    public boolean canHandle(Class<?> clazz) {
        return Object.class == clazz;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return new Object();
    }
}
