package org.custime.entertainment.killer.domain.event;

public class LostPlayerEvent {

    private final String playerName;

    public LostPlayerEvent(final String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
