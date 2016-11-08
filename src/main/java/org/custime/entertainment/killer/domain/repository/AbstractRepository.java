package org.custime.entertainment.killer.domain.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRepository<T> {
    private final ConcurrentHashMap<String, T> playerStore = new ConcurrentHashMap<>();

    public boolean save(final String id, final T t) {
        return playerStore.putIfAbsent(id, t) == null;
    }

    public Optional<T> getOne(final String id) {
        return Optional.ofNullable(playerStore.get(id));
    }

    public boolean remove(final String id) {
        return playerStore.remove(id) != null;
    }
}
