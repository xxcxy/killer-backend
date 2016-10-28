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
    private final EventBus eventBus;
    private final PlayerService playerService;

    public Player(final EventBus eventBus, final PlayerService playerService, final String name) {
        this.eventBus = eventBus;
        this.playerService = playerService;
        this.name = name;
        this.death = false;
        this.eventBus.register(this);
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
        eventBus.post(new PlayerVoteEvent(this, playerName));
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
