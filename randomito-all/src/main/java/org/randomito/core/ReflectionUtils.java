/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

import org.apache.commons.lang3.ClassUtils;
import org.randomito.core.exception.RandomitoException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.apache.commons.lang3.ArrayUtils.subarray;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Reflection Utils.
 *
 * @author Miron Balcerzak, 2017
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
        // unused
    }


    /**
     * makes {@link java.lang.reflect.Field} accessible.
     * <p>
     * See more:
     * {@link java.lang.reflect.AccessibleObject#setAccessible(boolean)}
     *
     * @param field - field to be accessed.
     * @return true if field is accessible.
     */
    public static boolean makeFieldAccessible(Field field) {
        if (!field.isAccessible()) {
            try {
                field.setAccessible(true);
            } catch (Exception ignore) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves value specified by path of provided object.
     * Example:
     * Given path: "address.street"
     * means that on the given object there is a field named "address"
     * that, once referenced, contains field named "street"
     *
     * @param instance - object to be checked
     * @param path     - path to be used
     * @return value used by the path.
     * @throws IllegalAccessException - exception thrown if field is innaccessible.
     */
    public static Object getPathValue(Object instance, String path) throws IllegalAccessException {
        if (path == null) {
            throw new RandomitoException("'path' cannot be null!");
        }
        Object ref = instance;
        for (String property : path.split("\\.")) {
            Field refField = getDeclaredField(ref.getClass(), property);
            if (!makeFieldAccessible(refField)) {
                throw new RandomitoException("Path cannot be accessed: " + path);
            }
            ref = refField.get(ref);
        }
        return ref;
    }

    /**
     * Sets given value specified by path of provided object.
     * Example:
     * Given path: "address.street"
     * means that on the given object there is a field named "address"
     * that, once referenced, contains field named "street"
     *
     * @param instance - object which contains the field.
     * @param path     - path to be used
     * @param value    - value to be set on the object.
     * @throws NoSuchMethodException     - exception
     * @throws IllegalAccessException    - exception
     * @throws InvocationTargetException - exception
     * @throws InstantiationException    - exception
     */
    public static void setPathValue(Object instance, String path, Object value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (path == null) {
            throw new RandomitoException("'path' cannot be null!");
        }
        Object ref = instance;
        Field refField = null;
        String[] split = path.split("\\.");
        for (int i = 0; i < split.length; i++) {
            String property = split[i];
            refField = getDeclaredField(ref.getClass(), property);
            if (!makeFieldAccessible(refField)) {
                throw new RandomitoException("Path cannot be accessed: " + path);
            }
            if (i < split.length - 1) {
                ref = refField.get(ref);
                if (ref == null) {
                    throw new RandomitoException("Property is null: '"
                            + join(subarray(split, 0, i + 1), ".")
                            + "'. Maybe increase 'depth' parameter?");
                }
            }
        }

        if (value == null) {
            refField.set(instance, null);
            return;
        }
        Class<?> type = refField.getType();
        if (type.isPrimitive()) {
            type = ClassUtils.primitiveToWrapper(type);
        }
        if (Number.class.isAssignableFrom(type)) {
            refField.set(ref, stringToNumber(value, type));
        } else {
            refField.set(ref, value);
        }
    }

    /**
     * creates a boxed type.
     *
     * @param value - string value of the number
     * @param clazz - desired boxed type
     * @param <T>   - any class inheriting {@link java.lang.Number}
     * @return instance of type T
     * @throws IllegalAccessException    - exception
     * @throws InvocationTargetException - exception
     * @throws InstantiationException    - exception
     * @throws NoSuchMethodException     - exception
     */
    public static <T> T stringToNumber(Object value, Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<T> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(String.valueOf(value));
    }

    /**
     * returns all available fields on the given class.
     *
     * @param clazz              - class to be used
     * @param inheritanceEnabled - flag specifying whether super class' fields should be mapped
     * @return fields {@link Field}
     */
    public static Field[] getDeclaredFields(Class<?> clazz, boolean inheritanceEnabled) {
        Set<Field> fields = new LinkedHashSet<>();
        do {
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && inheritanceEnabled);
        return fields.toArray(new Field[0]);
    }

    /**
     * Returns {@link Field} from the class or its super classes.
     *
     * @param clazz     - class to retrieve the field from
     * @param fieldName - field name
     * @return field
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        while (clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignore) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new RandomitoException("Field not found: " + fieldName);
    }

    /**
     * Retrieves type information from generic instance.
     * <p>
     * Example:
     * Map(String, Number) genericType.
     * Returns a primitive array of classes with values as follows:
     * <p>
     * array[0] = String.class
     * array[1] = Number.class
     *
     * @param field - generic field {@link Field}
     * @return array of classes.
     */
    public static Class[] getGenericTypes(Field field) {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type[] args = pType.getActualTypeArguments();
            Class[] genericTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                genericTypes[i] = (Class) args[i];
            }
            return genericTypes;
        } else {
            return new Class[0];
        }
    }

}
