/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.creator;

import com.google.common.collect.Maps;
import org.randomito.core.exception.RandomitoException;

import java.lang.reflect.Constructor;
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
     * @param type - class to be instantiated.
     * @return new instance of given type
     */
    public Object createForType(Class<?> type) {
        TypeCreator typeCreator = registry.get(type);
        if (typeCreator != null) {
            return typeCreator.newInstance();
        }
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RandomitoException("Failed to create object for type: " + type + ", try using @Random.TypeCreator() functionality");
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
