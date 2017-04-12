/*
 * Copyright (c) 2017 io.github.randomito contributors.
 * This program is made available under the terms of the "New BSD License".
 * SEE MORE: https://opensource.org/licenses/BSD-3-Clause
 */
package org.randomito.core;

import org.randomito.core.creator.TypeCreatorHolder;
import org.randomito.core.postprocessor.PostProcessor;
import org.randomito.core.scanner.AnnotationGenerationInfoScanner;
import org.randomito.core.scanner.AnnotationPostProcessorScanner;
import org.randomito.core.scanner.AnnotationScanner;
import org.randomito.core.scanner.AnnotationTypeCreatorScanner;

/**
 * Scans test for @Random annotations on available fields.
 * <p>
 * See more:
 * {@link org.randomito.core.scanner.AnnotationGenerationInfoScanner}
 * {@link org.randomito.core.scanner.AnnotationPostProcessorScanner}
 * {@link org.randomito.core.scanner.AnnotationTypeCreatorScanner}
 *
 * @author Miron Balcerzak, 2017
 */
public final class AnnotationConfigScanner implements AnnotationScanner<AnnotationConfigScanner.Result> {

    private final AnnotationGenerationInfoScanner generationInfoScanner = new AnnotationGenerationInfoScanner();
    private final AnnotationPostProcessorScanner postProcessorScanner = new AnnotationPostProcessorScanner();
    private final AnnotationTypeCreatorScanner typeCreatorScanner = new AnnotationTypeCreatorScanner();

    @Override
    public Result scan(Object instance) {
        return new Result(generationInfoScanner.scan(instance),
                postProcessorScanner.scan(instance),
                typeCreatorScanner.scan(instance));
    }

    public class Result {
        private final GenerationInfo[] generationInfo;
        private final PostProcessor[] postProcessors;
        private final TypeCreatorHolder[] typeCreators;

        Result(GenerationInfo[] generationInfo, PostProcessor[] postProcessors, TypeCreatorHolder[] typeCreators) {
            this.generationInfo = generationInfo;
            this.postProcessors = postProcessors;
            this.typeCreators = typeCreators;
        }

        public GenerationInfo[] getGenerationInfo() {
            return generationInfo;
        }

        public PostProcessor[] getPostProcessors() {
            return postProcessors;
        }

        public TypeCreatorHolder[] getTypeCreators() {
            return typeCreators;
        }
    }

}
