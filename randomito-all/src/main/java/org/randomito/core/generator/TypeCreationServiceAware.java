/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.creator.TypeCreatorService;

/**
 * Type generator can instantiate new instances (or delegate this operation to user through @Random.TypeCreator annotation)
 * To achieve that functionality, class should implement this interface and use injected instance of TypeCreatorService class.
 * <p>
 * See more:
 * {@link org.randomito.core.generator.TypeGenerator}
 *
 * @author Miron Balcerzak, 2017
 */
public interface TypeCreationServiceAware {

    /**
     * type creator service setter
     *
     * @param typeCreatorService - instance of service
     */
    void setTypeCreatorService(TypeCreatorService typeCreatorService);
}
