package org.custime.entertainment.killer.domain.value;

import org.custime.entertainment.killer.domain.model.Player;

public class PlayerVoteEvent {

    private final Player player;
    private final String votePlayerName;

    public PlayerVoteEvent(final Player player,
                           final String votePlayerName) {
        this.player = player;
        this.votePlayerName = votePlayerName;
    }

    public Player getPlayer() {
        return player;
    }

    public String getVotePlayerName() {
        return votePlayerName;
    }
}
