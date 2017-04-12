/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

/**
 * Base interface used by PostProcessors and TypeGenerators.
 * <p>
 * See more:
 * {@link org.randomito.core.generator.DefaultTypeGenerator}
 * {@link org.randomito.core.generator.TypeGenerationQueueAware}
 * {@link org.randomito.core.generator.PostProcessingQueueAware}
 *
 * @author Miron Balcerzak, 2017
 */
public interface QueueInserter {

    /**
     * adds object to the queue.
     * <p>
     * ProcessingQueue.OnProcessedEvent, if specified on QueueItem, is being called once processing is finished
     * of inserted object.
     *
     * @param ctx - context of object that is being added to the queue.
     * @return queued item, setOnProcessedEvent can be set.
     */
    ProcessingQueue.QueueItem addToQueue(DefaultContext ctx);

}