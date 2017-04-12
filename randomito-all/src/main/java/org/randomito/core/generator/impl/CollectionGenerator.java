/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.randomito.core.DefaultContext;
import org.randomito.core.ProcessingQueue;
import org.randomito.core.QueueInserter;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.generator.TypeCreationServiceAware;
import org.randomito.core.generator.TypeGenerationPredicate;
import org.randomito.core.generator.TypeGenerationQueueAware;
import org.randomito.core.generator.TypeGenerator;

import java.util.Collection;
import java.util.Set;

/**
 * Generator handles collection types - (all classes that inherit this interface)
 * <p>
 * See more: {@link java.util.Collection}
 *
 * @author Miron Balcerzak, 2017
 */
public class CollectionGenerator implements TypeGenerator, TypeGenerationPredicate, TypeGenerationQueueAware,
        TypeCreationServiceAware {

    private QueueInserter inserter;
    private TypeCreatorService typeCreatorService;

    public boolean canHandle(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        Class collectionType = Object.class;
        Class[] genericTypes = ReflectionUtils.getGenericTypes(ctx.getField());
        if (genericTypes.length > 0) {
            collectionType = genericTypes[0];
        }
        final Collection collection;
        if (ctx.getType().isInterface()) {
            collection = newCollection(ctx.getType());
        } else {
            collection = (Collection) typeCreatorService.createForType(ctx.getType());
        }

        for (int i = 0; i < 3; i++) {
            inserter.addToQueue(ctx.overrideType(collectionType))
                    .setOnProcesesedEvent(addToCollectionEvent(collection));
        }
        return collection;
    }

    private ProcessingQueue.OnProcessedEvent addToCollectionEvent(final Collection collection) {
        return new ProcessingQueue.OnProcessedEvent() {
            @Override
            @SuppressWarnings("unchecked")
            public void onProcessed(Object result) {
                collection.add(result);
            }
        };
    }

    private Collection newCollection(Class<?> clazz) {
        if (Set.class.isAssignableFrom(clazz)) {
            return Sets.newHashSet();
        }
        return Lists.newArrayList();
    }

    @Override
    public void setTypeGenerationQueueInserter(QueueInserter inserter) {
        this.inserter = inserter;
    }

    @Override
    public void setTypeCreatorService(TypeCreatorService typeCreatorService) {
        this.typeCreatorService = typeCreatorService;
    }
}
