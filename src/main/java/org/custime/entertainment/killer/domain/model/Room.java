package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.custime.entertainment.killer.domain.value.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Room {
    private List<Player> players;
    private Game game;
    private final EventBus eventBus;

    public Room(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
        players = new ArrayList<>();
    }

    public synchronized boolean startGame(final Map<Role, Integer> roleCount) {
        if (!allocate(players, roleCount)) {
            return false;
        }
        game = new Game(players, eventBus);
        eventBus.register(game);
        return true;
    }

    private boolean allocate(final List<Player> playerList, final Map<Role, Integer> roles) {
        final List<Role> roleList = generateRole(roles);
        if (playerList.size() != roleList.size()) {
            return false;
        }
        IntStream.range(0, playerList.size()).forEach(index -> playerList.get(index).setRole(roleList.get(index)));
        return true;
    }

    private List<Role> generateRole(final Map<Role, Integer> roles) {
        List<Role> result = new ArrayList<>();
        roles.forEach((role, i) -> IntStream.range(0, i).forEach(j -> result.add(role)));
        Collections.shuffle(result);
        return result;
    }

    public synchronized boolean addPlayer(final Player player) {
        return !isGameStarted() && registerPlayer(player) && players.add(player);
    }

    private boolean registerPlayer(final Player player) {
        eventBus.register(player);
        player.setEventBus(eventBus);
        return true;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public synchronized boolean removePlayer(final Player player) {
        return !isGameStarted() && unregisterPlayer(player) && players.remove(player);
    }

    private boolean unregisterPlayer(final Player player) {
        eventBus.unregister(player);
        player.setEventBus(null);
        return true;
    }

    @Subscribe
    private void listenGameFinish(final FinishGameEvent event) {
        eventBus.unregister(this.game);
        this.game = null;
    }

    public boolean isGameStarted() {
        return game != null;
    }
}
