package org.randomito.core.generator.impl;

import com.google.common.collect.Lists;
import org.randomito.core.ProcessingQueue;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.generator.PostProcessingQueueAware;
import org.randomito.core.generator.TypeCreationServiceAware;
import org.randomito.core.generator.TypeGenerationQueueAware;
import org.randomito.core.generator.TypeGenerator;

import java.util.List;

class BaseGenerationTest {

    private List<ProcessingQueue.QueueItem> list = Lists.newArrayList();

    public final <T extends TypeGenerator> T init(T generator) {
        if (generator instanceof TypeCreationServiceAware) {
            ((TypeCreationServiceAware) generator).setTypeCreatorService(new TypeCreatorService());
        }
        if (generator instanceof TypeGenerationQueueAware) {
            ((TypeGenerationQueueAware) generator).setTypeGenerationQueueInserter(new ProcessingQueue() {
                @Override
                protected Object processingAction(QueueItem queueItem) throws Exception {
                    return list.add(queueItem);
                }
            });
        }
        if (generator instanceof PostProcessingQueueAware) {
            ((PostProcessingQueueAware) generator).setPostProcessingQueueInserter(new ProcessingQueue() {
                @Override
                protected Object processingAction(QueueItem queueItem) throws Exception {
                    return list.add(queueItem);
                }
            });
        }
        return generator;
    }
}
