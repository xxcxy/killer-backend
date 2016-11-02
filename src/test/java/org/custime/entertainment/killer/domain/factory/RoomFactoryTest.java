package org.custime.entertainment.killer.domain.factory;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RoomFactoryTest {

    @Test
    public void testCreateRoom() {
        assertThat(new RoomFactory().createRoom(), notNullValue());
    }
}
