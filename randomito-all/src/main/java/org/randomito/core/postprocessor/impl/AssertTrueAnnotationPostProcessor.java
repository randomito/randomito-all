/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.impl;

import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import javax.validation.constraints.AssertTrue;

/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more: {@link javax.validation.constraints.AssertTrue}
 *
 * @author Miron Balcerzak, 2017
 */
public class AssertTrueAnnotationPostProcessor implements PostProcessor {

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(AssertTrue.class)) {
            return value;
        }
        return true;
    }
}
