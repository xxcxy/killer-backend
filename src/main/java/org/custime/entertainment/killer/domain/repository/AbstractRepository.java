package org.custime.entertainment.killer.domain.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRepository<T> {
    private final Map<String, T> playerStore = new ConcurrentHashMap<>();

    public boolean save(final String id, final T t) {
        playerStore.put(id, t);
        return true;
    }

    public Optional<T> getOne(final String id) {
        return Optional.ofNullable(playerStore.get(id));
    }

    public boolean remove(final String id) {
        return playerStore.remove(id) != null;
    }
}
