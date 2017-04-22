/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.creator;

import com.google.common.collect.Maps;
import org.randomito.core.exception.RandomitoException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Type creator service.
 * Provides clean way for class instantiation.
 *
 * @author Miron Balcerzak, 2017
 */
public class TypeCreatorService {

    private final Map<Class, TypeCreator> registry = Maps.newHashMap();

    /**
     * creates object of given type.
     *
     * @param instantor - object requesting the instantiation.
     * @param type      - type to create
     * @return new instance of given type
     */
    public Object createForType(Object instantor, Class<?> type) {
        try {
            return newInstance(instantor, type);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RandomitoException("Failed to create object for type: " + type + ", try using @Random.TypeCreator() functionality");
        }
    }

    private Object newInstance(Object instantor, Class<?> type)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TypeCreator typeCreator = registry.get(type);
        if (typeCreator != null) {
            return typeCreator.newInstance();
        }
        if (type.isMemberClass()) {
            Constructor<?> constructor = type.getDeclaredConstructor(instantor.getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(instantor);
        } else {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        }
    }

    /**
     * registers type creator for given type
     *
     * @param type    - class o be instantiated
     * @param creator - creator for type
     */
    public void register(Class type, TypeCreator creator) {
        registry.put(type, creator);
    }
}
