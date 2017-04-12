/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.QueueInserter;

/**
 * Type generator can schedule object post processing.
 * To achieve that functionality, class should implement this interface and add type into queue.
 * <p>
 * See more:
 * {@link org.randomito.core.generator.TypeGenerator}
 *
 * @author Miron Balcerzak, 2017
 */
public interface PostProcessingQueueAware {

    /**
     * post processing queue inserter setter
     *
     * @param queueInserter - instance of queue
     */
    void setPostProcessingQueueInserter(QueueInserter queueInserter);
}
