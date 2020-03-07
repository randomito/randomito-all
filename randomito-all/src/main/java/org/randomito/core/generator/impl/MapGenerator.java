/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.ProcessingQueue;
import org.randomito.core.QueueInserter;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.generator.TypeCreationServiceAware;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerationQueueAware;
import org.randomito.core.generator.TypeGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all types implementating Map interface.
 * <p>
 * See more: {@link java.util.Map}
 *
 * @author Miron Balcerzak, 2017
 */
public class MapGenerator implements TypeGenerator, TypeGenerationPredicate, TypeGenerationQueueAware, TypeCreationServiceAware {

    private QueueInserter queueInserter;
    private TypeCreatorService typeCreatorService;

    @Override
    public boolean canHandle(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object generate(final DefaultContext ctx) throws Exception {
        final Map map;
        if (ctx.getField().getType().isInterface()) {
            map = new HashMap();
        } else {
            map = (Map) typeCreatorService.createForType(ctx.getRef(), ctx.getField().getType());
        }
        Class[] genericTypes = ReflectionUtils.getGenericTypes(ctx.getField());
        final Class keyClass;
        final Class valueClass;
        if (genericTypes.length == 0) {
            keyClass = Object.class;
            valueClass = Object.class;
        } else {
            keyClass = genericTypes[0];
            valueClass = genericTypes[1];
        }
        for (int i = 0; i < 3; i++) {
            queueInserter.addToQueue(ctx.overrideType(keyClass))
                    .setOnProcesesedEvent(processValueEvent(ctx, map, valueClass));
        }
        return map;
    }

    private ProcessingQueue.OnProcessedEvent processValueEvent(final DefaultContext ctx, final Map map, final Class valueClass) {
        return new ProcessingQueue.OnProcessedEvent() {
            @Override
            public void onProcessed(final Object key) throws RandomitoException {
                queueInserter.addToQueue(ctx.overrideType(valueClass))
                        .setOnProcesesedEvent(addToMapEvent(key));
            }

            private ProcessingQueue.OnProcessedEvent addToMapEvent(final Object key) {
                return new ProcessingQueue.OnProcessedEvent() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public void onProcessed(final Object value) throws RandomitoException {
                        map.put(key, value);
                    }
                };
            }
        };
    }

    @Override
    public void setTypeGenerationQueueInserter(QueueInserter inserter) {
        this.queueInserter = inserter;
    }

    @Override
    public void setTypeCreatorService(TypeCreatorService typeCreatorService) {
        this.typeCreatorService = typeCreatorService;
    }
}
