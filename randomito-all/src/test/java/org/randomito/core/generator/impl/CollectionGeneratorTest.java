package org.randomito.core.generator.impl;

import org.junit.Before;
import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.test.utils.TestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionGeneratorTest extends BaseGenerationTest {

    private CollectionGenerator generation;

    @Before
    public void init() {
        generation = init(new CollectionGenerator());
    }


    @Test
    public void testCanHandle() {
        // assert true
        assertTrue(generation.canHandle(Collection.class));
        assertTrue(generation.canHandle(List.class));
        assertTrue(generation.canHandle(Set.class));
        assertTrue(generation.canHandle(LinkedList.class));
    }

    @Test
    public void testCanHandle_false() {
        // given
        Class<CollectionGeneratorTest> aClass = CollectionGeneratorTest.class;

        // when
        boolean retVal = generation.canHandle(aClass);

        // then
        assertThat(retVal, is(false));
    }

    @Test
    public void testGenerate_collection() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "collection");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(ArrayList.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_list() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "list");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(ArrayList.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_set() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "set");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(HashSet.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_linkedList() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "linkedlist");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(LinkedList.class.isAssignableFrom(generated.getClass()));
    }

    @Test
    public void testGenerate_collectionOfStrings() throws Exception {
        // given
        DefaultContext ctx = TestUtils.createCtx(new TestingClass(), "collectionOfStrings");

        // when
        Object generated = generation.generate(ctx);

        // then
        assertThat(generated, is(notNullValue()));
        assertTrue(ArrayList.class.isAssignableFrom(generated.getClass()));
    }

    static class TestingClass {
        private Collection collection;
        private Collection<String> collectionOfStrings;
        private List list;
        private Set set;
        private LinkedList linkedlist;

    }

}