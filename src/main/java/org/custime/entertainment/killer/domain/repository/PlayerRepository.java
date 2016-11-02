package org.custime.entertainment.killer.domain.repository;

import org.custime.entertainment.killer.domain.model.Player;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerRepository {

    private static final PlayerRepository INSTANCE = new PlayerRepository();

    private PlayerRepository() {
    }

    private final Map<String, Player> playerStore = new ConcurrentHashMap<>();

    public boolean save(final String id, final Player player) {
        playerStore.put(id, player);
        return true;
    }

    public Optional<Player> getOne(final String id) {
        return Optional.ofNullable(playerStore.get(id));
    }

    public boolean remove(final String id) {
        return playerStore.remove(id) != null;
    }

    public static PlayerRepository getInstance() {
        return INSTANCE;
    }
}
