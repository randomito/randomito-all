package org.randomito;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.randomito.annotation.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MockitoIntegrationTest {

    @Random
    @Random.SetValues({
            @Random.SetValue(property = "id", value = "1"),
            @Random.SetValue(property = "presetValue", value = "not-generated")
    })
    private Entity entity;

    @Mock
    private Service service;

    @Before
    public void beforeTest() {
        Randomito.init(this);
    }

    @Test
    public void testMockitoIntegration() {
        // given
        Mockito.doReturn(entity).when(service).getEntity(1L);
        // when
        Entity entity = service.getEntity(1L);
        // then
        assertThat(entity.id, is(1L));
        assertThat(entity.presetValue, is("not-generated"));
        assertThat(entity.generatedValue, is(notNullValue()));
    }

    static class Entity {
        Long id;
        String presetValue;
        String generatedValue;
    }

    static class Service {
        Entity getEntity(Long id) {
            throw new NotImplementedException("Boom!");
        }
    }
}