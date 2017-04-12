/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

/**
 * Type generation predicate class.
 * Should be used in case of more complex type value condition (or multiple conditions)
 * <p>
 * See more:
 * {@link org.randomito.core.generator.TypeGenerator}
 *
 * @author Miron Balcerzak, 2017
 */
public interface TypeGenerationPredicate {

    /**
     * checks whether type generator instance can handle given class.
     *
     * @param type - type
     * @return true if type generator can handle given type
     */
    boolean canHandle(Class<?> type);
}
