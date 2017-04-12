/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.creator;

/**
 * Type creator interface. Provides a way to instantiate an object without default constructor.
 * <p>
 * See more:
 * {@link org.randomito.annotation.Random.TypeCreator}
 *
 * @author Miron Balcerzak, 2017
 */
public interface TypeCreator {

    /**
     * New instance method.
     *
     * @return new instance of object
     */
    Object newInstance();

}
