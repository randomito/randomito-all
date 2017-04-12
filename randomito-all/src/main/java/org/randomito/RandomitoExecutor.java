/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito;

import org.randomito.core.DefaultContext;
import org.randomito.core.ProcessingQueue;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.generator.TypeGeneratorDelegator;
import org.randomito.core.postprocessor.PostProcessorExecutor;

import java.lang.reflect.Field;

/**
 * Randomito Executor.
 *
 * @author Miron Balcerzak, 2017
 */
class RandomitoExecutor {

    private final ProcessingQueue typeGenerationQueue;
    private final ProcessingQueue postProcessingQueue;
    private final PostProcessorExecutor postProcessorExecutor;
    private final TypeGeneratorDelegator typeGeneratorDelegator;
    private final TypeCreatorService typeCreatorService;
    private boolean throwExceptions = false;

    RandomitoExecutor() {
        this.postProcessorExecutor = new PostProcessorExecutor();
        this.typeCreatorService = new TypeCreatorService();
        this.typeGeneratorDelegator = new TypeGeneratorDelegator();
        this.typeGenerationQueue = createTypeGenerationQueue();
        this.postProcessingQueue = createPostProcessingQueue();
        this.typeGeneratorDelegator.setPostProcessingQueueInserter(postProcessingQueue);
        this.typeGeneratorDelegator.setTypeGenerationQueueInserter(typeGenerationQueue);
        this.typeGeneratorDelegator.setTypeCreatorService(typeCreatorService);
    }

    void execute(DefaultContext... contexts) {
        for (final DefaultContext ctx : contexts) {
            typeGenerationQueue.addToQueue(ctx);
            postProcessingQueue.addToQueue(ctx);
        }
        processQueue(typeGenerationQueue);
        processQueue(postProcessingQueue);
    }

    private void processQueue(ProcessingQueue queue) {
        while (queue.isProcessing()) {
            try {
                queue.process();
            } catch (Exception e) {
                if (throwExceptions) {
                    throw new RandomitoException(e);
                } else {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private ProcessingQueue createPostProcessingQueue() {
        return new ProcessingQueue() {

            @Override
            public Object processingAction(QueueItem item) throws Exception {
                DefaultContext ctx = item.getContext();
                return postProcessorExecutor.postprocess(ctx);
            }
        };
    }

    private ProcessingQueue createTypeGenerationQueue() {
        return new ProcessingQueue() {

            @Override
            public Object processingAction(QueueItem item) throws Exception {
                DefaultContext ctx = item.getContext();
                Field field = ctx.getField();
                ReflectionUtils.makeFieldAccessible(field);
                Object generate = typeGeneratorDelegator.generate(ctx);
                if (item.getOnProcessedEvent() == null) {
                    field.set(ctx.getRef(), generate);
                } else {
                    item.getOnProcessedEvent().onProcessed(generate);
                }
                return generate;
            }
        };
    }

    PostProcessorExecutor getPostProcessorExecutor() {
        return postProcessorExecutor;
    }

    TypeGeneratorDelegator getTypeGenerators() {
        return typeGeneratorDelegator;
    }

    TypeCreatorService getTypeCreatorService() {
        return typeCreatorService;
    }

    void throwExceptions(boolean throwExc) {
        this.throwExceptions = throwExc;
    }

}
