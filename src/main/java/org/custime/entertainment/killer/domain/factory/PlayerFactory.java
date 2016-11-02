package org.custime.entertainment.killer.domain.factory;

import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.service.PlayerService;

import java.util.Objects;

public class PlayerFactory {

    public Player createPlayer(final PlayerService playerService) {
        Objects.requireNonNull(playerService);
        return new Player(playerService);
    }
}
