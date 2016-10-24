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
    public void testAddPlayerFailWhenGameStarted() {
        room.addPlayer(Utils.getPlayer());
        room.startGame();
        assertThat(room.addPlayer(Utils.getPlayer()), is(false));
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testRemovePlayer() {
        Player player = Utils.getPlayer();
        room.addPlayer(player);
        assertThat(room.getPlayers().size(), is(1));
        assertThat(room.removePlayer(player), is(true));
        assertThat(room.getPlayers().size(), is(0));
    }

    @Test
    public void testRemovePlayerFailWhenGameStarted() {
        Player player = Utils.getPlayer();
        room.addPlayer(player);
        room.startGame();
        assertThat(room.removePlayer(player), is(false));
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testStartGame() {
        room.addPlayer(Utils.getPlayer());
        Game game = room.startGame();
        assertThat(game, notNullValue());
    }

}
