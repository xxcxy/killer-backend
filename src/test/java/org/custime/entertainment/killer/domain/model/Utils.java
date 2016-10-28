package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.repository.RoomRepository;
import org.custime.entertainment.killer.domain.service.PlayerService;

import java.util.List;

import static org.mockito.Mockito.mock;

public class Utils {

    private static RoomRepository roomRepository = new RoomRepository();

    public static Room getRoom() {
        return roomRepository.createRoom();
    }

    public static Room getRoomWithPlayers() {
        Room room = getRoom();
        room.addPlayer(getPlayer());
        return room;
    }

    public static Room getRoomWithPlayers(final EventBus eventBus) {
        Room room = new Room(eventBus);
        room.addPlayer(getPlayer());
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
        return new Player(eventBus, playerService, playerName);
    }

    public static Game getGame() {
        return getRoomWithPlayers().startGame();
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
        return new Game(players, collector, roundProcessor, eventBus);
    }

    public static PlayerVoteCollector getPlayerVoteCollector(final List<Player> playerList) {
        return getPlayerVoteCollector(playerList, new EventBus());
    }

    public static PlayerVoteCollector getPlayerVoteCollector(final List<Player> playerList,
                                                             final EventBus eventBus) {
        return new PlayerVoteCollector(playerList, eventBus);
    }
}
