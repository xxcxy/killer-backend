package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RoomTest {

    private Room room;
    private Room roomWithPlayers;

    @Before
    public void setup() {
        room = Utils.getRoom();
        roomWithPlayers = Utils.getRoomWithPlayers();
    }

    @Test
    public void testAddPlayer() {
        room.addPlayer(Utils.getPlayer());
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testAddPlayerFailWhenGameStarted() {
        roomWithPlayers.startGame();
        assertThat(roomWithPlayers.addPlayer(Utils.getPlayer()), is(false));
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
        Game game = roomWithPlayers.startGame();
        assertThat(roomWithPlayers.isGameStarted(), is(true));
        assertThat(game, notNullValue());
    }

    @Test
    public void testAddPlayerAfterGameStopped() {
        EventBus eventBus = new EventBus();
        Utils.getRoomWithPlayers(eventBus).startGame();
        eventBus.post(new FinishGameEvent());
        assertThat(roomWithPlayers.addPlayer(Utils.getPlayer()), is(true));
    }
}
