package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import org.assertj.core.util.Lists;
import org.custime.entertainment.killer.domain.repository.RoomRepository;

import java.util.List;

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

    public static Player getPlayer() {
        return new Player();
    }

    public static Game getGame() {
        return getRoomWithPlayers().startGame();
    }

    public static Game getGame(final Player player, final PlayerVoteCollector collector) {
        return new Game(Lists.newArrayList(player), collector, new EventBus());
    }

    public static PlayerVoteCollector getPlayerVoteCollector(final List<Player> playerList) {
        return new PlayerVoteCollector(playerList, new EventBus());
    }
}
