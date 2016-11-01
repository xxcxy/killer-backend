package org.custime.entertainment.killer.domain.value;

public class ChangeGameStateEvent {
    private final GameState currentState;

    public ChangeGameStateEvent(final GameState gameState) {
        this.currentState = gameState;
    }

}
