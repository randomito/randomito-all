/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.jsr303;

import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import javax.validation.constraints.Null;

/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more:
 * {@link javax.validation.constraints.Null}
 * <p>
 * Notification:
 * {@link javax.validation.constraints.NotNull} is not handled as all generated values are not null by default.
 *
 * @author Miron Balcerzak, 2017
 */
public class NullAnnotationPostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Null.class)) {
            return value;
        }
        return null;
    }
}
