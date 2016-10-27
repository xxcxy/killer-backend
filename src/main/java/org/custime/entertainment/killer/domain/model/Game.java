package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.custime.entertainment.killer.domain.value.GameState;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.custime.entertainment.killer.domain.value.GameState.STARTED;

public class Game {
    private final List<Player> players;
    private final EventBus eventBus;
    private GameState state;
    private final AtomicBoolean isFinished;
    private final AtomicBoolean isVoting;
    private final PlayerVoteCollector voteCollector;

    public Game(final List<Player> players, final EventBus eventBus) {
        this(players, new PlayerVoteCollector(players, eventBus), eventBus);
    }

    protected Game(final List<Player> players,
                   final PlayerVoteCollector playerVoteCollector,
                   final EventBus eventBus) {
        this.players = players;
        this.voteCollector = playerVoteCollector;
        this.eventBus = eventBus;
        this.eventBus.register(this);
        this.isFinished = new AtomicBoolean(false);
        this.isVoting = new AtomicBoolean(false);
        this.state = STARTED;
        players.forEach(player -> player.setVoteConsumer(this::vote));
        doAndNextStep();
    }

    private void vote(final Player player, final String playerName) {
        if (isVoting.get()) {
            voteCollector.collectVote(player, playerName);
            mayBeVoteFinished();
        }
    }

    private void mayBeVoteFinished() {
        if (voteCollector.isFinished() && isVoting.compareAndSet(true, false)) {
            doAndNextStep();
        }
    }

    private void doAndNextStep() {
        voteCollector.clear();
        state = state.next();
        isVoting.set(true);
    }

    public void finish() {
        if (isFinished.compareAndSet(false, true)) {
            eventBus.post(new FinishGameEvent());
        }
    }
}
