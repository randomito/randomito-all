package org.randomito.core.postprocessor.impl;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DecimalMinMaxAnnotationPostProcessorTest extends BasePostProcessorTest {

    public DecimalMinMaxAnnotationPostProcessorTest() {
        super(new DecimalMinMaxAnnotationPostProcessor());
    }

    @Test
    public void testBigDecimal() {
        // given
        Wrapper obj = new Wrapper();

        // when
        BigDecimal decimal = postprocess(obj, "decimal", BigDecimal.class);

        // then
        Assert.assertTrue(decimal.compareTo(new BigDecimal(19)) == 1);
        Assert.assertTrue(decimal.compareTo(new BigDecimal(30)) == -1);
    }

    @Test
    public void testBigInteger() {
        // given
        Wrapper obj = new Wrapper();

        // when
        BigInteger bigInteger = postprocess(obj, "bigInteger", BigInteger.class);

        // then
        Assert.assertTrue(bigInteger.compareTo(new BigInteger("19")) == 1);
        Assert.assertTrue(bigInteger.compareTo(new BigInteger("30")) == -1);
    }

    @Test
    public void testInteger() {
        // given
        Wrapper obj = new Wrapper();

        // when
        Integer integer = postprocess(obj, "anInt", Integer.class);

        // then
        Assert.assertTrue(integer > 19 && integer < 30);
    }

    public static class Wrapper {

        @DecimalMin("20")
        @DecimalMax("30")
        private BigDecimal decimal = new BigDecimal(0);

        @DecimalMin("20")
        @DecimalMax("30")
        private BigInteger bigInteger = new BigInteger("0");

        @DecimalMin("20")
        @DecimalMax("30")
        private Integer anInt = 0;
    }
}