/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

import org.randomito.core.postprocessor.AnnotationInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * DefaultContext for postprocessing/generation operations.
 * Contains data concerning field triggering generation, type of generation and instance itself.
 * <p>
 * CAUTION !!
 * Should NEVER be created with "new". All processors/generators already
 * contain instance of the context, so user of the API should spawn an instance
 * with {@link DefaultContext#createChild(Object, Field)} or {@link DefaultContext#overrideType(Class)}
 *
 * @author Miron Balcerzak, 2017
 */
public class DefaultContext implements AnnotationInfo {

    private final DefaultContext parentCtx;
    private final Object ref;
    private final Field field;
    private final GenerationInfo info;
    private Class<?> type;

    public DefaultContext(Object ref, Field field, GenerationInfo info) {
        this(ref, field, null, info, null);
    }

    private DefaultContext(Object ref, Field field, Class<?> type, GenerationInfo info, DefaultContext parentCtx) {
        this.ref = ref;
        this.field = field;
        this.type = type;
        this.info = info;
        this.parentCtx = parentCtx;
    }

    public Object getRef() {
        return ref;
    }

    public Field getField() {
        return field;
    }

    public Class<?> getType() {
        if (type != null) {
            return type;
        }
        return field.getType();
    }

    public DefaultContext getParent() {
        return parentCtx;
    }

    public GenerationInfo getInfo() {
        return info;
    }

    public DefaultContext createChild(Object childRef, Field field) {
        return new DefaultContext(childRef, field, null, info, this);
    }

    public DefaultContext overrideType(Class<?> type) {
        return new DefaultContext(ref, field, type, info, parentCtx);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
        return field.isAnnotationPresent(annotation);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return field.getAnnotation(annotation);
    }
}
