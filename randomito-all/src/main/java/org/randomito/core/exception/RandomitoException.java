/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.exception;

/**
 * Exception class used across the system.
 *
 * @author Miron Balcerzak, 2017
 */
public class RandomitoException extends RuntimeException {

    public RandomitoException() {
    }

    public RandomitoException(String message) {
        super(message);
    }

    public RandomitoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RandomitoException(Throwable cause) {
        super(cause);
    }
}
