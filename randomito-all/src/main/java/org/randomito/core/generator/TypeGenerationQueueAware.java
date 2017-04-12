/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.QueueInserter;

/**
 * Type generator can delegate creation of other objects.
 * To achieve that functionality, class should implement this interface and add type onto queue.
 * <p>
 * See more:
 * {@link org.randomito.core.QueueInserter}
 *
 * @author Miron Balcerzak, 2017
 */

public interface TypeGenerationQueueAware {
    /**
     * type generation queue inserter setter
     *
     * @param inserter - instance of queue
     */
    void setTypeGenerationQueueInserter(QueueInserter inserter);
}
