/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.DefaultContext;
import org.randomito.core.QueueInserter;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.exception.RandomitoException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper for TypeGenerator interfaces.
 *
 * @author Miron Balcerzak, 2017
 */
public class DefaultTypeGenerator implements TypeGenerator, TypeGenerationPredicate,
        TypeGenerationQueueAware, PostProcessingQueueAware, TypeCreationServiceAware {

    private final TypeGenerator generator;
    private final TypeGenerationPredicate predicate;

    public DefaultTypeGenerator(final TypeGenerator generator, final Class... handledClasses) {
        this.generator = generator;
        if (generator instanceof TypeGenerationPredicate) {
            this.predicate = (TypeGenerationPredicate) generator;
        } else {
            this.predicate = new HandledClassesPrecondition(handledClasses);
        }
    }

    @Override
    public boolean canHandle(final Class<?> clazz) {
        return predicate.canHandle(clazz);
    }

    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return generator.generate(ctx);
    }

    @Override
    public void setTypeGenerationQueueInserter(QueueInserter inserter) {
        if (generator instanceof TypeGenerationQueueAware) {
            ((TypeGenerationQueueAware) generator).setTypeGenerationQueueInserter(inserter);
        }
    }

    @Override
    public void setPostProcessingQueueInserter(QueueInserter inserter) {
        if (generator instanceof PostProcessingQueueAware) {
            ((PostProcessingQueueAware) generator).setPostProcessingQueueInserter(inserter);
        }
    }

    @Override
    public void setTypeCreatorService(TypeCreatorService typeCreatorService) {
        if (generator instanceof TypeCreationServiceAware) {
            ((TypeCreationServiceAware) generator).setTypeCreatorService(typeCreatorService);
        }
    }

    /**
     * Handled Classes Precondition
     */
    private class HandledClassesPrecondition implements TypeGenerationPredicate {

        private final Set<Class> handledClasses = new HashSet<>();

        HandledClassesPrecondition(Class[] handledClasses) {
            if (handledClasses == null) {
                throw new RandomitoException("Argument cannot be null");
            }
            Collections.addAll(this.handledClasses, handledClasses);
        }

        @Override
        public boolean canHandle(final Class<?> clazz) {
            return this.handledClasses.contains(clazz);
        }

    }
}