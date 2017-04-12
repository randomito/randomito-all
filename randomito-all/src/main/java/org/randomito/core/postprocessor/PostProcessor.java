/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor;

/**
 * Post processor base interface.
 * DefaultContext class is implementing this interface and is being passed into post processor's
 * <p>
 * See more:
 * {@link org.randomito.core.postprocessor.AnnotationInfo}
 * {@link org.randomito.core.postprocessor.PostProcessorExecutor}
 * {@link org.randomito.core.DefaultContext}
 *
 * @author Miron Balcerzak, 2017
 */
public interface PostProcessor {

    /**
     * processes generated value
     *
     * @param ctx   - context for postprocessing
     * @param value - value to be processed
     * @return new value.
     * @throws Exception - all exceptions during post processing.
     */
    Object process(AnnotationInfo ctx, Object value) throws Exception;
}
