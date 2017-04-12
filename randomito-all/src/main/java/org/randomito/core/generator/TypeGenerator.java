/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.DefaultContext;

/**
 * Type generation interface.
 *
 * @author Miron Balcerzak, 2017
 */
public interface TypeGenerator {

    /**
     * Generates object base on provided values.
     *
     * @param ctx - specification of new object (and it's values).
     * @return - instance of randomed object.
     * @throws Exception - error that occurs during data generation.
     */
    Object generate(DefaultContext ctx) throws Exception;
}
