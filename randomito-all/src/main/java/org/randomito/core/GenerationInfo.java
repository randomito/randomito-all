/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

/**
 * Data transfer object - contains all information that annotation scanner managed to retrieve from the fields.
 * <p>
 * See more:
 * {@link org.randomito.core.AnnotationConfigScanner}
 *
 * @author Miron Balcerzak, 2017
 */
public class GenerationInfo {

    private final String name;
    private final int depth;
    private final boolean inheritanceAllowed;
    private final boolean skip;

    public GenerationInfo(String name, int depth, boolean inheritance, boolean skip) {
        this.name = name;
        this.depth = depth;
        this.inheritanceAllowed = inheritance;
        this.skip = skip;
    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isInheritanceAllowed() {
        return inheritanceAllowed;
    }

    public boolean isSkip() {
        return skip;
    }

}
