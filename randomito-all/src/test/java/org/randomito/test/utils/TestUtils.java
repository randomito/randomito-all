package org.randomito.test.utils;

import org.randomito.core.DefaultContext;
import org.randomito.core.GenerationInfo;

public final class TestUtils {

    private TestUtils() {
    }


    public final static DefaultContext createCtx(Object obj, String fieldname) {
        try {
            return new DefaultContext(obj, obj.getClass().getDeclaredField(fieldname),
                    new GenerationInfo("triggered_by_TestUtils", 5, true, false));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
