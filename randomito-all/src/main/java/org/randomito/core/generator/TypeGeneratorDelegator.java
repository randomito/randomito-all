/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.generator;

import org.randomito.core.DefaultContext;
import org.randomito.core.QueueInserter;
import org.randomito.core.creator.TypeCreatorService;
import org.randomito.core.generator.impl.ArrayGenerator;
import org.randomito.core.generator.impl.BooleanGenerator;
import org.randomito.core.generator.impl.CharacterGenerator;
import org.randomito.core.generator.impl.CollectionGenerator;
import org.randomito.core.generator.impl.ComplexTypeGenerator;
import org.randomito.core.generator.impl.DateGenerator;
import org.randomito.core.generator.impl.EnumGenerator;
import org.randomito.core.generator.impl.MapGenerator;
import org.randomito.core.generator.impl.NullGenerator;
import org.randomito.core.generator.impl.NumberGenerator;
import org.randomito.core.generator.impl.ObjectGenerator;
import org.randomito.core.generator.impl.StringGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Delegates creation of object - entry point for type generation.
 * <p>
 * See more:
 * {@link org.randomito.core.generator.TypeGenerator}
 * {@link org.randomito.core.generator.TypeGenerationQueueAware}
 * {@link org.randomito.core.generator.PostProcessingQueueAware}
 *
 * @author Miron Balcerzak, 2017
 */
public class TypeGeneratorDelegator
        implements TypeGenerator, TypeGenerationQueueAware, PostProcessingQueueAware, TypeCreationServiceAware {

    private final List<DefaultTypeGenerator> generators = new LinkedList<>();
    private final Map<Class<?>, TypeGenerator> cache = new HashMap<>();
    private QueueInserter typeGenerationQueue;
    private QueueInserter postProcessingQueue;
    private TypeCreatorService typeCreatorService;

    public static void registerDefaultGenerators(TypeGeneratorDelegator generators) {
        generators.register(new StringGenerator());
        generators.register(new ObjectGenerator());
        generators.register(new BooleanGenerator());
        generators.register(new ArrayGenerator());
        generators.register(new CharacterGenerator());
        generators.register(new DateGenerator());
        generators.register(new EnumGenerator());
        generators.register(new NumberGenerator());
        generators.register(new CollectionGenerator());
        generators.register(new MapGenerator());
        generators.register(new ComplexTypeGenerator());
        generators.register(new NullGenerator());
    }

    // delegation
    @Override
    public Object generate(DefaultContext ctx) throws Exception {
        return getRandomGenerator(ctx.getType()).generate(ctx);
    }

    private TypeGenerator getRandomGenerator(Class<?> type) {
        TypeGenerator generator = null;
        if (cache.containsKey(type)) {
            generator = cache.get(type);
        } else {
            for (DefaultTypeGenerator randomGenerator : generators) {
                if (randomGenerator.canHandle(type)) {
                    generator = randomGenerator;
                    break;
                }
            }
            cache.put(type, generator);
        }
        return generator;
    }

    public void register(TypeGenerator generator) {
        DefaultTypeGenerator defaultTypeGenerator = new DefaultTypeGenerator(generator);
        defaultTypeGenerator.setTypeGenerationQueueInserter(typeGenerationQueue);
        defaultTypeGenerator.setPostProcessingQueueInserter(postProcessingQueue);
        defaultTypeGenerator.setTypeCreatorService(typeCreatorService);
        generators.add(defaultTypeGenerator);
    }

    @Override
    public void setTypeGenerationQueueInserter(QueueInserter inserter) {
        this.typeGenerationQueue = inserter;
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
