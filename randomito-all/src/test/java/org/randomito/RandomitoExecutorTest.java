package org.randomito;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.randomito.core.DefaultContext;
import org.randomito.core.GenerationInfo;
import org.randomito.core.ReflectionUtils;
import org.randomito.core.generator.impl.NumberGenerator;
import org.randomito.core.postprocessor.impl.MinMaxAnnotationPostProcessor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RandomitoExecutorTest {

    RandomitoExecutor executor;

    @Before
    public void init() {
        executor = new RandomitoExecutor();
    }

    @Test
    public void testExecutionWithMinMaxPostProcessor() {
        // given
        Wrapper instance = new Wrapper();
        GenerationInfo gi = new GenerationInfo("anInt", 1, true, false);
        executor.getTypeGenerators().register(new NumberGenerator());
        executor.getPostProcessorExecutor().register(new MinMaxAnnotationPostProcessor());
        // when

        executor.execute(new DefaultContext(instance, ReflectionUtils.getDeclaredField(instance, "anInt"), gi));

        // then
        Assert.assertThat(instance.anInt, is(notNullValue()));
        Assert.assertThat(instance.anInt, Matchers.greaterThanOrEqualTo(1));
        Assert.assertThat(instance.anInt, Matchers.lessThan(5));
    }

    static class Wrapper {

        @Min(1)
        @Max(5)
        private Integer anInt;

    }


}