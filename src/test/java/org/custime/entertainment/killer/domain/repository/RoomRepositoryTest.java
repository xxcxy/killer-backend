package org.custime.entertainment.killer.domain.repository;

import org.custime.entertainment.killer.domain.model.Room;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RoomRepositoryTest {

    private RoomRepository roomRepository = new RoomRepository();

    @Test
    public void testCreateRoom() {
        Room room = roomRepository.createRoom();
        assertThat(room, notNullValue());
    }
}
