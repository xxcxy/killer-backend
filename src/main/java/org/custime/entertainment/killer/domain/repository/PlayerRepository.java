package org.custime.entertainment.killer.domain.repository;

import org.custime.entertainment.killer.domain.model.Player;

public final class PlayerRepository extends AbstractRepository<Player> {

    private static final PlayerRepository INSTANCE = new PlayerRepository();

    private PlayerRepository() {
    }

    public static PlayerRepository getInstance() {
        return INSTANCE;
    }
}
