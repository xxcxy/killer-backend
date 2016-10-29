package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.service.PlayerService;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.custime.entertainment.killer.domain.value.PlayerVoteEvent;
import org.custime.entertainment.killer.domain.value.UpdatePlayerStateEvent;

public class Player {

    private final String name;
    private boolean death;
    private EventBus eventBus;
    private final PlayerService playerService;

    public Player(final PlayerService playerService, final String name) {
        this.playerService = playerService;
        this.name = name;
        this.death = false;
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    private void reset(final FinishGameEvent event) {
        death = false;
    }

    @Subscribe
    private void updatePlayerState(final UpdatePlayerStateEvent event) {
        playerService.updatePlayersState(event.getStateMap());
    }

    public void vote(final String playerName) {
        if (eventBus != null) {
            eventBus.post(new PlayerVoteEvent(this, playerName));
        }
    }

    public String getName() {
        return name;
    }

    public void die() {
        death = true;
    }

    public boolean isDeath() {
        return death;
    }
}
