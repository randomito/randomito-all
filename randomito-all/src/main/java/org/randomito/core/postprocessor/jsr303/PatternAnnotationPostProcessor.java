/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core.postprocessor.jsr303;

import org.randomito.core.postprocessor.AnnotationInfo;
import org.randomito.core.postprocessor.PostProcessor;

import javax.validation.constraints.Pattern;


/**
 * JSR-303 Validation annotation handling way.
 * <p>
 * See more: {@link javax.validation.constraints.Pattern}
 *
 * @author Miron Balcerzak, 2017
 */
public class PatternAnnotationPostProcessor implements PostProcessor {

//    private final static int REGEX_GENERATION_TRY = 2000;

    @Override
    public Object process(AnnotationInfo ctx, Object value) throws Exception {
        if (!ctx.isAnnotationPresent(Pattern.class)) {
            return value;
        }
//        String regexp = ctx.getAnnotation(Pattern.class).regexp();
//        Generex generex = new Generex(regexp);
//        for (int i = 0; i < REGEX_GENERATION_TRY; i++) {
//            try {
//                return generex.random();
//            } catch (StackOverflowError e) {
//                //TODO: fix Generex#prepareRandom() causing stackoverflow
//                // known bug in Generex lib...let's try again.
//            }
//        }
//        System.out.println("failed to generate regex: " + regexp);
        return value;
    }
}
