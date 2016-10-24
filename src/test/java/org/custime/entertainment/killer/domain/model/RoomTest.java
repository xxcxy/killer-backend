package org.custime.entertainment.killer.domain.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RoomTest {

    private Room room;

    @Before
    public void setup() {
        room = Utils.getRoom();
    }

    @Test
    public void testAddPlayer() {
        room.addPlayer(Utils.getPlayer());
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testRemovePlayer() {
        Player player = Utils.getPlayer();
        room.addPlayer(player);
        assertThat(room.getPlayers().size(), is(1));
        room.removePlayer(player);
        assertThat(room.getPlayers().size(), is(0));

    }

    @Test
    public void testStartGame() {
        Game game = room.startGame();
        assertThat(game, notNullValue());
    }

}
