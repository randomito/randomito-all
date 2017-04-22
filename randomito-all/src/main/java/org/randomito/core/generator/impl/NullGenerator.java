package org.randomito.core.generator.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerator;

/**
 * Generator handles all type of types - and does nothing with them.
 * <p>
 * See more: {@link java.lang.Character}
 *
 * @author Miron Balcerzak, 2017
 */
public class NullGenerator implements TypeGenerator, TypeGenerationPredicate {

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        System.out.println("NULL value for type: " + ctx.getField().getType());
        return Void.TYPE;
    }

    @Override
    public boolean canHandle(Class<?> type) {
        return true;
    }
}
