package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.value.PlayerVoteEvent;

public class Player {

    private final EventBus eventBus;

    public Player(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void vote(final String playerName) {
        eventBus.post(new PlayerVoteEvent(this, playerName));
    }

}
