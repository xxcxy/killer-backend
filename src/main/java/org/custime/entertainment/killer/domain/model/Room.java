package org.custime.entertainment.killer.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Player> players;

    public Room() {
        players = new ArrayList<>();
    }

    public Game startGame() {
        return new Game();
    }

    public void addPlayer(final Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
