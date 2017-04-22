/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

import org.randomito.core.exception.RandomitoException;

import java.util.LinkedList;

/**
 * Object processing queue (FIFO). Used by type generators and post processor.
 *
 * @author Miron Balcerzak, 2017
 */
public abstract class ProcessingQueue implements QueueInserter {

    private final LinkedList<QueueItem> queue = new LinkedList<>();

    protected abstract Object processingAction(QueueItem queueItem) throws Exception;

    @Override
    public QueueItem addToQueue(DefaultContext ctx) {
        QueueItem queueItem = new QueueItem(ctx);
        queue.addLast(queueItem);
        return queueItem;
    }

    public boolean isProcessing() {
        return !queue.isEmpty();
    }

    public void process() throws Exception {
        QueueItem queueItem = queue.removeFirst();
        processingAction(queueItem);
    }

    public interface OnProcessedEvent {

        void onProcessed(Object processResult) throws RandomitoException;
    }

    public final class QueueItem {

        private final DefaultContext ctx;
        private OnProcessedEvent event;

        QueueItem(DefaultContext ctx) {
            this.ctx = ctx;
        }

        public DefaultContext getContext() {
            return ctx;
        }

        public void setOnProcesesedEvent(OnProcessedEvent event) {
            this.event = event;
        }

        public OnProcessedEvent getOnProcessedEvent() {
            return event;
        }
    }

}
