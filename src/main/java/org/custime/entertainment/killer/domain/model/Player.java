package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.event.FinishGameEvent;
import org.custime.entertainment.killer.domain.event.LosePlayerEvent;
import org.custime.entertainment.killer.domain.event.PlayerVoteEvent;
import org.custime.entertainment.killer.domain.event.UpdatePlayerStateEvent;
import org.custime.entertainment.killer.domain.service.PlayerService;
import org.custime.entertainment.killer.domain.value.Role;

public class Player {

    private String name;
    private boolean death;
    private boolean offline;
    private EventBus eventBus;
    private Role role;
    private final PlayerService playerService;

    public Player(final PlayerService playerService) {
        this.playerService = playerService;
        this.death = false;
        this.offline = false;
    }

    void setEventBus(final EventBus eventBus) {
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

    public void setName(final String name) {
        this.name = name;
    }

    public void die() {
        death = true;
    }

    public Role getRole() {
        return role;
    }

    void setRole(final Role role) {
        this.role = role;
    }

    public boolean isDeath() {
        return death;
    }

    public void offline() {
        offline = true;
        eventBus.post(new LosePlayerEvent(name));
    }

    public boolean isOffline() {
        return offline;
    }
}
