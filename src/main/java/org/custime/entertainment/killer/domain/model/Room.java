package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private List<Player> players;
    private Game game;
    private final EventBus eventBus;

    public Room(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
        players = new ArrayList<>();
    }

    public synchronized Game startGame() {
        game = new Game(players, eventBus);
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

    @Subscribe
    private void listenGameFinish(final FinishGameEvent event) {
        this.game = null;
    }

    public boolean isGameStarted() {
        return game != null;
    }
}
