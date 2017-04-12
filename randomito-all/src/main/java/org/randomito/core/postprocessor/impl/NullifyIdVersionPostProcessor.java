/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Handles {@link javax.persistence.Id} and {@link javax.persistence.Version} of persistence api.
 * Sets fields annotated by above to null.
 * <p>
 * See more:
 * {@link javax.persistence.Id}
 * {@link javax.persistence.Version}
 *
 * @author Miron Balcerzak, 2017
 */
public class NullifyIdVersionPostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) {
        if (ctx.isAnnotationPresent(Id.class)
                || ctx.isAnnotationPresent(Version.class)) {
            return null;
        }
        return value;
    }
}
