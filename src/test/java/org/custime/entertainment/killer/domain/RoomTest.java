package org.custime.entertainment.killer.domain;

import org.custime.entertainment.killer.domain.model.Game;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.model.Room;
import org.custime.entertainment.killer.domain.repository.RoomRepository;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RoomTest {

    private RoomRepository roomRepository = new RoomRepository();

    @Test
    public void testCreateRoom() {
        Room room = roomRepository.createRoom();
        assertThat(room, notNullValue());
    }

    @Test
    public void testAddPlayer() {
        Room room = roomRepository.createRoom();
        room.addPlayer(new Player());
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testStartGame() {
        Room room = roomRepository.createRoom();
        Game game = room.startGame();
        assertThat(game, notNullValue());
    }

}
