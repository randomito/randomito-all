package org.randomito.core.generator.impl;

import org.junit.Before;
import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MapGeneratorTest extends BaseGenerationTest {

    private MapGenerator generator;

    @Before
    public void init() {
        generator = init(new MapGenerator());
    }

    @Test
    public void testCanHandle() {
        // assert true
        assertTrue(generator.canHandle(Map.class));
        assertTrue(generator.canHandle(HashMap.class));
        assertTrue(generator.canHandle(LinkedHashMap.class));
    }

    @Test
    public void testCanHandle_false() {
        // given
        Class<MapGeneratorTest> aClass = MapGeneratorTest.class;

        // when
        boolean retVal = generator.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testGenerate_map() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "map");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(HashMap.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_hashmap() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "hashmap");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(HashMap.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_linkedHashMap() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "linkedHashMap");

        // when
        Object generated = generator.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(LinkedHashMap.class.isAssignableFrom(generated.getClass()));
    }

    static class TestingClass {
        private Map map;
        private HashMap hashmap;
        private LinkedHashMap<String, String> linkedHashMap;
    }

}