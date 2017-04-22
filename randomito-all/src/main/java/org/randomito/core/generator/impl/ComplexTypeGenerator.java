/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.QueueInserter;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.generator.PostProcessingQueueAware;
import org.randomito.core.generator.TypeCreationServiceAware;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerationQueueAware;
import org.randomito.core.generator.TypeGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.lang.reflect.Field;

import static org.randomito.core.ReflectionUtils.getDeclaredFields;
import static org.randomito.core.ReflectionUtils.makeFieldAccessible;

/**
 * Handles POJO objects. All fields of the object are being delegated for creation.
 * Default constructor is preferred way for object instantiation.
 * <p>
 * In case of missing default constructor, user needs to provide @Random.TypeGenerator
 * <p>
 * See more: {@link org.randomito.annotation.Random}
 *
 * @author Miron Balcerzak, 2017
 */
public class ComplexTypeGenerator implements TypeGenerator, TypeGenerationPredicate,
        TypeGenerationQueueAware, PostProcessingQueueAware, TypeCreationServiceAware {

    private TypeCreatorService typeCreatorService;
    private QueueInserter generationQueue;
    private QueueInserter postProcessingQueue;

    public boolean canHandle(Class<?> clazz) {
        return !clazz.isPrimitive() && clazz != Object.class;
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        // handling for @Configurable entities
        if (isInjectable(ctx.getField())) {
            return null;
        }

        Object obj = typeCreatorService.createForType(ctx.getRef(), ctx.getType());
        if (count(ctx) == ctx.getInfo().getDepth()) {
            return null;
        }
        for (Field inner : getDeclaredFields(ctx.getType(), ctx.getInfo().isInheritanceAllowed())) {
            if (isInjectable(inner)) {
                continue;
            }
            if (!makeFieldAccessible(inner)) {
                continue;
            }
            if (inner.get(obj) != null && !inner.getType().isPrimitive()) {
                continue;
            }
            generationQueue.addToQueue(ctx.createChild(obj, inner));
        }
        postProcessingQueue.addToQueue(ctx.createChild(obj, null));
        return obj;
    }

    private int count(DefaultContext ctx) {
        int refCount = 0;
        while (ctx.getParent() != null) {
            refCount = refCount + 1;
            ctx = ctx.getParent();
        }
        return refCount;
    }

    private boolean isInjectable(Field field) {
        return field.isAnnotationPresent(Autowired.class)
                || field.isAnnotationPresent(Inject.class);
    }

    @Override
    public void setTypeGenerationQueueInserter(QueueInserter inserter) {
        this.generationQueue = inserter;
    }

    @Override
    public void setPostProcessingQueueInserter(QueueInserter queueInserter) {
        this.postProcessingQueue = queueInserter;
    }

    @Override
    public void setTypeCreatorService(TypeCreatorService typeCreatorService) {
        this.typeCreatorService = typeCreatorService;
    }
}
