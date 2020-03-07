/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.creator;

import org.randomito.annotation.Random;

/**
 * Data transfer object mapping values from @Random.TypeCreator annotation.
 * See more:
 * {@link Random.TypeCreator}
 * {@link org.randomito.core.creator.TypeCreatorService}
 *
 * @author Miron Balcerzak, 2017
 */
public class TypeCreatorHolder {

    private final Class<?> type;

    private final TypeCreator creator;

    public TypeCreatorHolder(Class<?> type, TypeCreator creatorDelegator) {
        this.type = type;
        this.creator = creatorDelegator;
    }

    public Class<?> getType() {
        return type;
    }

    public TypeCreator getCreator() {
        return creator;
    }
}
