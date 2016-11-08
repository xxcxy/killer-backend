package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.factory.RoomFactory;
import org.custime.entertainment.killer.domain.service.PlayerService;

import java.util.List;

import static org.mockito.Mockito.mock;

public class Utils {

    private static RoomFactory roomFactory = new RoomFactory();

    public static Room getRoom() {
        return roomFactory.createRoom();
    }

    public static Room getRoomWithPlayers() {
        Room room = getRoom();
        room.addPlayer(getPlayer());
        return room;
    }

    public static Room getRoomWithPlayers(final EventBus eventBus) {
        Room room = new Room(eventBus);
        room.addPlayer(getPlayer(eventBus));
        return room;
    }

    public static Player getPlayer() {
        return getPlayer(new EventBus());
    }

    public static Player getPlayer(final EventBus eventBus) {
        return getPlayer(eventBus, mock(PlayerService.class));
    }

    public static Player getPlayer(final EventBus eventBus, final PlayerService playerService) {
        return getPlayer(eventBus, playerService, "playerName");
    }

    public static Player getPlayer(final EventBus eventBus, final String playerName) {
        return getPlayer(eventBus, mock(PlayerService.class), playerName);
    }

    public static Player getPlayer(final EventBus eventBus,
                                   final PlayerService playerService,
                                   final String playerName) {
        Player player = new Player(playerService);
        player.setName(playerName);
        eventBus.register(player);
        player.setEventBus(eventBus);
        return player;
    }

    public static Game getGame(final Player player,
                               final PlayerVoteCollector collector,
                               final RoundProcessor roundProcessor,
                               final EventBus eventBus) {
        return getGame(Lists.newArrayList(player), collector, roundProcessor, eventBus);
    }

    public static Game getGame(final List<Player> players,
                               final PlayerVoteCollector collector,
                               final RoundProcessor roundProcessor,
                               final EventBus eventBus) {
        Game game = new Game(players, collector, roundProcessor, eventBus);
        eventBus.register(game);
        return game;
    }

    public static PlayerVoteCollector getPlayerVoteCollector(final List<Player> playerList,
                                                             final EventBus eventBus) {
        return new PlayerVoteCollector(playerList, eventBus);
    }
}
