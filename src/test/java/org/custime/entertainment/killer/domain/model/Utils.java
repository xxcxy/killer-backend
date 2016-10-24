package org.custime.entertainment.killer.domain.model;

import org.custime.entertainment.killer.domain.repository.RoomRepository;

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
}
