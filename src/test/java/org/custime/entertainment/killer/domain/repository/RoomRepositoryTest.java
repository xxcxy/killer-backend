package org.custime.entertainment.killer.domain.repository;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RoomRepositoryTest {

    private RoomRepository roomRepository = RoomRepository.getInstance();

    @Test
    public void testRepositoryGetInstance() {
        assertThat(RoomRepository.getInstance() == roomRepository, is(true));
    }

}
