package org.custime.entertainment.killer.domain.event;

public class LosePlayerEvent {

    private final String playerName;

    public LosePlayerEvent(final String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
