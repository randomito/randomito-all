/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation @Random.
 *
 * @author Miron Balcerzak, 2017
 * @since Randomito 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Random {

    String NULL = "____JUST_A_NULL_VALUE_MARKER____";

    /**
     * recursive creation, default to only 1 layer.
     * Example:
     * class Inner {
     * .  String inner;
     * }
     * <p>
     * class A {
     * .  String someString;
     * .  Inner innerObject;
     * }
     * <p>
     * class A, contains instance of Inner class
     * default mechanism won't populate this field and all fields belonging to this instance,
     * inner class is null
     *
     * @return value of recursive calls
     */
    int depth() default 1;

    /**
     * Enables inherited fields random data feeling, if "false" - only pointed entity is filled in.
     *
     * @return true/false
     */
    boolean inheritanceAllowed() default true;

    /**
     * Skips object generation if set to true.
     *
     * @return true/false
     */
    boolean skip() default false;

    /**
     * marker class for custom PostProcessors
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface PostProcessor {
    }

    /**
     * marker class for type creator functionality
     * * See more: {@link org.randomito.core.creator.TypeCreator}
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface TypeCreator {
        Class forType();
    }

    /**
     * Grouping annotation for @SetValue
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SetValues {
        SetValue[] value();
    }

    /**
     * SetValue annotation. Depending on the configuration, will insert one instance into another
     * or set the value of field
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SetValue {

        /**
         * property name in type to be set.
         *
         * @return property property
         */
        String property();

        /**
         * constant inject, works for simple types
         *
         * @return objects injects
         */
        String value() default "";

        /**
         * property from test which is to be inserted into the property.
         *
         * @return property property from test class
         */
        String referenceBy() default "";
    }

}
