package org.randomito.core.postprocessor.impl;

import org.junit.Test;

import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by miciek on 07.04.2017.
 */
public class NullifyIdVersionPostProcessorTest extends BasePostProcessorTest {


    public NullifyIdVersionPostProcessorTest() {
        super(new NullifyIdVersionPostProcessor());
    }

    @Test
    public void testId() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Long id = postprocess(wrapper, "id", Long.class);

        // then
        assertThat(id, is(nullValue()));
    }

    @Test
    public void testVersion() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Date version = postprocess(wrapper, "version", Date.class);

        // then
        assertThat(version, is(nullValue()));
    }

    @Test
    public void testNoAnnotation() {
        // given
        Wrapper wrapper = new Wrapper();

        // when
        Long aLong = postprocess(wrapper, "aLong", Long.class);

        // then
        assertThat(aLong, is(notNullValue()));
    }

    static class Wrapper {

        @Id
        private Long id = 10l;

        @Version
        private Date version = new Date();

        private Long aLong = 10l;
    }
}