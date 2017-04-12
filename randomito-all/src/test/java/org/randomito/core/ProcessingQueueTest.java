package org.randomito.core;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.randomito.test.utils.TestUtils;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProcessingQueueTest {

    @Test
    public void testIsProcessed() {
        // given
        DefaultContext ctx = TestUtils.createCtx(new Wrapper(), "anInt");
        ProcessingQueue processingQueue = new ProcessingQueue() {
            @Override
            protected Object processingAction(QueueItem queueItem) throws Exception {
                return null;
            }
        };

        // when
        processingQueue.addToQueue(ctx);

        // then
        assertThat(processingQueue.isProcessing(), is(true));
    }

    @Test
    public void testProcessingAction() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new Wrapper(), "anInt");
        final List<DefaultContext> queueItems = Lists.newLinkedList();

        ProcessingQueue processingQueue = new ProcessingQueue() {
            @Override
            protected Object processingAction(QueueItem queueItem) throws Exception {
                queueItems.add(queueItem.getContext());
                return null;
            }
        };
        processingQueue.addToQueue(ctx);

        // when
        processingQueue.process();

        // then
        assertThat(queueItems, is(Matchers.hasSize(1)));
        assertThat(queueItems.get(0), is(ctx));
    }

    static class Wrapper {
        private int anInt;
    }
}