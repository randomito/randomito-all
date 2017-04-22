package org.randomito.core.creator;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.randomito.core.exception.RandomitoException;

public class TypeCreatorServiceTest {

    private TypeCreatorService service = new TypeCreatorService();

    @Test
    public void testRegisterAndCreateForType() {
        // given
        Class<TypeCreatorServiceTest> type = TypeCreatorServiceTest.class;
        TypeCreator creator = new TypeCreator() {
            @Override
            public Object newInstance() {
                return new TypeCreatorServiceTest();
            }
        };

        // when
        service.register(type, creator);
        Object forType = service.createForType(null, type);

        // then
        Assert.assertThat(forType, CoreMatchers.is(CoreMatchers.notNullValue()));
        Assert.assertTrue(forType.getClass() == type);
    }


    @Test
    public void testCreateForType() {
        // given
        Class<TypeCreatorServiceTest> type = TypeCreatorServiceTest.class;

        // when
        Object forType = service.createForType(null, type);

        // then
        Assert.assertThat(forType, CoreMatchers.is(CoreMatchers.notNullValue()));
        Assert.assertTrue(forType.getClass() == type);
    }

    @Test(expected = RandomitoException.class)
    public void testCreateForType_exception() {
        // given
        Class<ClassWithNoDefConstructor> type = ClassWithNoDefConstructor.class;

        // when
        Object forType = service.createForType(null, type);

        // then
        Assert.assertThat(forType, CoreMatchers.is(CoreMatchers.notNullValue()));
        Assert.assertTrue(forType.getClass() == type);
    }


    static class ClassWithNoDefConstructor {

        ClassWithNoDefConstructor(String str) {
        }
    }
}