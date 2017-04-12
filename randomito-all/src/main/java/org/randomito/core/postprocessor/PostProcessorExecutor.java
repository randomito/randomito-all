/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.randomito.core.DefaultContext;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.postprocessor.impl.AssertFalseAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.AssertTrueAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.DecimalMinMaxAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.DigitsAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.FutureAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.InjectReferenceByPostProcessor;
import org.randomito.core.postprocessor.impl.MinMaxAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.NullAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.PastAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.SizeAnnotationPostProcessor;
import org.randomito.core.postprocessor.impl.StaticValuePostProcessor;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Post Processing chain.
 * <p>
 * See more:
 * {@link org.randomito.core.postprocessor.PostProcessor}
 *
 * @author Miron Balcerzak, 2017
 */
public class PostProcessorExecutor {

    private final Map<Class<?>, PostProcessor> registry = Maps.newLinkedHashMap();
    private final Set<Class<?>> excluded = Sets.newHashSet();

    public static void registerJsr303(PostProcessorExecutor postProcessorExecutor) {
        postProcessorExecutor.register(new NullAnnotationPostProcessor());
        postProcessorExecutor.register(new AssertFalseAnnotationPostProcessor());
        postProcessorExecutor.register(new AssertTrueAnnotationPostProcessor());
//        postProcessorExecutor.register(new PatternAnnotationPostProcessor());
        postProcessorExecutor.register(new DigitsAnnotationPostProcessor());
        postProcessorExecutor.register(new MinMaxAnnotationPostProcessor());
        postProcessorExecutor.register(new SizeAnnotationPostProcessor());
        postProcessorExecutor.register(new DecimalMinMaxAnnotationPostProcessor());
        postProcessorExecutor.register(new FutureAnnotationPostProcessor());
        postProcessorExecutor.register(new PastAnnotationPostProcessor());
    }

    public static void registerCorePostProcessors(PostProcessorExecutor postProcessorExecutor) {
        postProcessorExecutor.register(new StaticValuePostProcessor());
        postProcessorExecutor.register(new InjectReferenceByPostProcessor());
    }

    @SuppressWarnings("unchecked")
    public Object postprocess(DefaultContext ctx) throws Exception {
        Object instance = ctx.getRef();
        if (instance == null) {
            return null;
        }
        if (excluded.contains(instance.getClass())) {
            return instance;
        }
        Field[] fields;
        if (ctx.getParent() == null) {
            fields = ArrayUtils.toArray(ctx.getField());
        } else {
            fields = ReflectionUtils.getDeclaredFields(instance.getClass(), true);
        }

        for (PostProcessor postprocessor : registry.values()) {
            for (Field field : fields) {
                if (!ReflectionUtils.makeFieldAccessible(field)) {
                    continue;
                }
                Object o = field.get(instance);
                if (o == null) {
                    continue;
                }
                Object postprocessed = postprocessor.process(ctx.createChild(instance, field), o);
                field.set(instance, postprocessed);
            }

        }
        return instance;
    }

    public void register(PostProcessor postProcessor) {
        registry.put(postProcessor.getClass(), postProcessor);
    }

    public void unregister(Class<? extends PostProcessor> clazz) {
        registry.remove(clazz);
    }

    public void excludeClasses(Class<?>... clazzez) {
        Collections.addAll(excluded, clazzez);
    }

}
