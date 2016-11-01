package org.custime.entertainment.killer.domain.event;

import com.google.common.collect.Sets;

import java.util.Set;

public class KillPlayerEvent {

    private final Set<String> playerNames;

    public KillPlayerEvent(final String... playerNames) {
        this.playerNames = Sets.newHashSet(playerNames);
    }

    public boolean addPlayer(final String playerName) {
        return playerName != null && playerNames.add(playerName);
    }

    public boolean removePlayer(final String playerName) {
        return playerName != null && playerNames.remove(playerName);
    }

    public Set<String> getPlayerNames() {
        return playerNames;
    }
}
