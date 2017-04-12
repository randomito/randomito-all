/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.randomito.core.AnnotationConfigScanner;
import org.randomito.core.DefaultContext;
import org.randomito.core.GenerationInfo;
import org.randomito.core.creator.TypeCreatorHolder;
import org.randomito.core.generator.TypeGeneratorDelegator;
import org.randomito.core.postprocessor.PostProcessor;
import org.randomito.core.postprocessor.PostProcessorExecutor;
import org.randomito.core.postprocessor.impl.NullifyIdVersionPostProcessor;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Set;

import static org.randomito.core.ReflectionUtils.getDeclaredField;

/**
 * Convenience class. Contains all necessary methods simplifying process of random objects generation.
 *
 * @author Miron Balcerzak, 2017
 * @since Randomito 1.0
 */
public final class Randomito {

    private Randomito() {
    }

    /**
     * inits annotation scan and processes class.
     *
     * @param instance which constains @Random group annotations.
     */
    public static void init(Object instance) {
        new Builder().init(instance);
    }

    /**
     * returns randomito instance, with default depth of 1.
     *
     * @param object to be randomito, new instance is being returned.
     * @param <T>    type of object.
     * @return new instance with random values.
     */
    @SuppressWarnings("unchecked")
    public static <T> T random(T object) {
        return (T) new Builder().random(object.getClass(), 1);
    }

    /**
     * returns randomito instance, with default depth of 1.
     *
     * @param object to be randomito, new instance is being returned.
     * @param depth  - depth of randomization process.
     * @param <T>    type of object.
     * @return new instance with random values for given depth.
     */
    @SuppressWarnings("unchecked")
    public static <T> T random(T object, int depth) {
        return (T) new Builder().random(object.getClass(), depth);
    }

    /**
     * returns randomito instance, with default depth of 1.
     *
     * @param clazz - class to be randomito.
     * @param <T>    type of object.
     * @return new instance with random values.
     */
    public static <T> T random(Class<T> clazz) {
        return new Builder().random(clazz, 1);
    }

    /**
     * returns randomito instance.
     *
     * @param clazz - class to be randomito.
     * @param <T>    type of object.
     * @return new instance with random values for given depth.
     */
    public static <T> T random(Class<T> clazz, int depth) {
        return new Builder().random(clazz, depth);
    }

    /**
     * inits annotation scan and processes class.
     * @return Builder instance
     */
    public static Builder custom() {
        return new Builder();
    }

    public final static class Builder {

        private boolean disableExceptions = false;
        private boolean disableJsr303 = false;
        private boolean disablePersistenceAnnotations = false;
        private Set<Class<?>> excluded = Sets.newHashSet();

        private Builder() {

        }

        /**
         * Disables process of setting @Id and @Version fields to null if called.
         *
         * @return "this" instance of builder, must end with init() or random().
         */
        public Builder disableNullifyIdAndVersion() {
            disablePersistenceAnnotations = true;
            return this;
        }

        /**
         * Excludes given classes from post processing.
         *
         * @param classes - coma separated classes to be excluded from tests.
         * @return "this" instance of builder, must end with init() or random().
         */
        public Builder excludeFromPostProcessing(Class<?>... classes) {
            Collections.addAll(excluded, classes);
            return this;
        }

        /**
         * Disables exceptions from being thrown, instead are being printed out to standard output.
         * <p>
         * CAUTION:
         * May drastically decrease the performance!!
         *
         * @return "this" instance of builder, must end with init() or random().
         */
        public Builder disableExceptions() {
            this.disableExceptions = true;
            return this;
        }

        /**
         * disables all JSR-303 Validation post processing.
         *
         * @return "this" instance of builder, must end with init() or random().
         */
        public Builder disableJsr303() {
            this.disableJsr303 = true;
            return this;
        }

        /**
         * Inits the framework, scans the class for annotations.
         * and generates the values and inserts them into the testing unit.
         *
         * @param instance - should be passed "this" reference of JUnit test class.
         */
        public void init(final Object instance) {
            final RandomitoExecutor executor = createRandomObjectExecutor();
            AnnotationConfigScanner.Result results = new AnnotationConfigScanner().scan(instance);
            for (PostProcessor postProcessor : results.getPostProcessors()) {
                executor.getPostProcessorExecutor().register(postProcessor);
            }
            for (TypeCreatorHolder creator : results.getTypeCreators()) {
                executor.getTypeCreatorService().register(creator.getType(), creator.getCreator());
            }
            DefaultContext[] contexts = FluentIterable.from(results.getGenerationInfo())
                    .transform(new Function<GenerationInfo, DefaultContext>() {
                        @Override
                        public DefaultContext apply(GenerationInfo info) {
                            Field field = getDeclaredField(instance, info.getName());
                            return new DefaultContext(instance, field, info);
                        }
                    }).toArray(DefaultContext.class);
            executor.execute(contexts);
        }

        /**
         * creates a single instance of randomito object.
         *
         * @param clazz    - type to be randomito.
         * @param depth    - depth of randomization.
         * @param <T>      - type of the class.
         * @return - new instance, with randomito values.
         */
        public <T> T random(Class<T> clazz, final int depth) {
            RandomitoExecutor executor = createRandomObjectExecutor();
            Object instance = executor.getTypeCreatorService().createForType(clazz);
            RandomizationWrapper wrapper = new RandomizationWrapper(instance);
            DefaultContext ctx = new DefaultContext(wrapper, getDeclaredField(wrapper, RandomizationWrapper.INSTANCE_FIELD),
                    new GenerationInfo(RandomizationWrapper.INSTANCE_FIELD, depth, true, false))
                    .overrideType(instance.getClass());
            executor.execute(ctx);
            return clazz.cast(wrapper.instance);
        }

        private RandomitoExecutor createRandomObjectExecutor() {
            RandomitoExecutor executor = new RandomitoExecutor();
            executor.throwExceptions(!disableExceptions);
            executor.getPostProcessorExecutor().excludeClasses(Iterables.toArray(excluded, Class.class));
            if (!disableJsr303) {
                PostProcessorExecutor.registerJsr303(executor.getPostProcessorExecutor());
            }
            if (!disablePersistenceAnnotations) {
                executor.getPostProcessorExecutor().register(new NullifyIdVersionPostProcessor());
            }
            PostProcessorExecutor.registerCorePostProcessors(executor.getPostProcessorExecutor());
            TypeGeneratorDelegator.registerDefaultGenerators(executor.getTypeGenerators());
            return executor;
        }

        private final static class RandomizationWrapper {
            final static String INSTANCE_FIELD = "instance";

            Object instance;

            public RandomizationWrapper(Object instance) {
                this.instance = instance;
            }
        }

    }
}
