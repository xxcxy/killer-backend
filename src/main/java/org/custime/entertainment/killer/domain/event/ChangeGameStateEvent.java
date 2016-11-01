package org.custime.entertainment.killer.domain.event;

import org.custime.entertainment.killer.domain.value.GameState;

public class ChangeGameStateEvent {
    private final GameState currentState;

    public ChangeGameStateEvent(final GameState gameState) {
        this.currentState = gameState;
    }

}
