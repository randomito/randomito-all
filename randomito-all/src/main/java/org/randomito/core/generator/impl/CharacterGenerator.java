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
 * Generator handles char types (boxed/unboxed).
 * <p>
 * See more: {@link java.lang.Character}
 *
 * @author Miron Balcerzak, 2017
 */
public class CharacterGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public boolean canHandle(final Class<?> clazz) {
        return Character.class == clazz || Character.TYPE == clazz;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return (char) RandomUtils.nextInt(0, 255);
    }
}