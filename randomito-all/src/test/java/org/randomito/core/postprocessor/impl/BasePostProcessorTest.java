package org.randomito.core.postprocessor.impl;

import org.randomito.core.DefaultContext;
import org.randomito.core.exception.RandomitoException;
import org.randomito.core.postprocessor.PostProcessor;
import org.randomito.test.utils.TestUtils;

import java.lang.reflect.Field;

public class BasePostProcessorTest {

    private final PostProcessor postProcessor;


    public BasePostProcessorTest(PostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    public <T> T postprocess(Object obj, String fieldname, Class<T> retVal) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldname);
            declaredField.setAccessible(true);
            DefaultContext ctx = TestUtils.createCtx(obj, fieldname);
            Object processed = postProcessor.process(ctx, declaredField.get(obj));
            return retVal.cast(processed);
        } catch (RandomitoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}