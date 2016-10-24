package org.custime.entertainment.killer.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private List<Player> players;
    private Game game;

    public Room() {
        players = new ArrayList<>();
    }

    public synchronized Game startGame() {
        game = new Game();
        return game;
    }

    public synchronized boolean addPlayer(final Player player) {
        return !isGameStarted() && players.add(player);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public synchronized boolean removePlayer(final Player player) {
        return !isGameStarted() && players.remove(player);
    }

    private synchronized boolean isGameStarted() {
        return game != null;
    }
}
