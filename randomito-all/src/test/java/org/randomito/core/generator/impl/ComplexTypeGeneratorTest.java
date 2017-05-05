package org.randomito.core.generator.impl;

import org.junit.Before;
import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.core.ReflectionUtils;
import org.randomito.test.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ComplexTypeGeneratorTest extends BaseGenerationTest {

    private ComplexTypeGenerator generator;

    @Before
    public void init() {
        generator = init(new ComplexTypeGenerator());
    }

    @Test
    public void testCanHandle() {
        // given
        Class<ComplexTypeGenerator> aClass = ComplexTypeGenerator.class;

        // when
        boolean retVal = generator.canHandle(aClass);

        // then
        assertThat(retVal, is(true));
    }

    @Test
    public void testCanHandle_false() {
        // given
        Class<Integer> aClass = int.class;

        // when
        boolean retVal = generator.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testGenerate_complexType() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "object");
        ReflectionUtils.makeFieldAccessible(ctx.getField());

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(ComplexType.class == generated.getClass());
    }

    public static class TestingClass {
        private ComplexType object;
    }

    public static class ComplexType {
        private final String VALUE = "string-value";
        @Inject
        InjectableService service1;
        @Autowired
        InjectableService service2;
        private String string;
        private boolean bool;
        private ComplexType object;
    }

    public static class InjectableService {

    }

}