package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.event.FinishGameEvent;
import org.junit.Before;
import org.junit.Test;

import static org.custime.entertainment.killer.domain.value.Role.VILLAGER;
import static org.hamcrest.CoreMatchers.is;
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
        roomWithPlayers.startGame(ImmutableMap.of(VILLAGER, 1));
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
        room.startGame(ImmutableMap.of(VILLAGER, 1));
        assertThat(room.removePlayer(player), is(false));
        assertThat(room.getPlayers().size(), is(1));
    }

    @Test
    public void testStartGame() {
        assertThat(roomWithPlayers.startGame(ImmutableMap.of(VILLAGER, 1)), is(true));
        assertThat(roomWithPlayers.isGameStarted(), is(true));
    }

    @Test
    public void testStartFailWhenGameHasStarted() {
        assertThat(roomWithPlayers.startGame(ImmutableMap.of(VILLAGER, 1)), is(true));
        assertThat(roomWithPlayers.isGameStarted(), is(true));
        assertThat(roomWithPlayers.startGame(ImmutableMap.of(VILLAGER, 1)), is(false));
    }

    @Test
    public void testStartFailWhenRoleCountDiffPlayerCount() {
        assertThat(roomWithPlayers.startGame(ImmutableMap.of(VILLAGER, 2)), is(false));
        assertThat(roomWithPlayers.isGameStarted(), is(false));
    }

    @Test
    public void testAddPlayerAfterGameStopped() {
        EventBus eventBus = new EventBus();
        Utils.getRoomWithPlayers(eventBus).startGame(ImmutableMap.of(VILLAGER, 1));
        eventBus.post(new FinishGameEvent());
        assertThat(roomWithPlayers.addPlayer(Utils.getPlayer()), is(true));
    }

    @Test
    public void testRemoveOfflinePlayerAfterGameStopped() {
        EventBus eventBus = new EventBus();
        Room rm = Utils.getRoomWithPlayers(eventBus);
        rm.startGame(ImmutableMap.of(VILLAGER, 1));
        rm.getPlayers().forEach(player -> player.offline());
        assertThat(rm.getPlayers().size(), is(1));
        eventBus.post(new FinishGameEvent());
        assertThat(rm.getPlayers().size(), is(0));
    }

    @Test
    public void testRemoveOfflinePlayer() {
        EventBus eventBus = new EventBus();
        Room rm = Utils.getRoomWithPlayers(eventBus);
        rm.getPlayers().get(0).offline();
        assertThat(rm.getPlayers().size(), is(0));
    }
}
