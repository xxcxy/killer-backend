package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.custime.entertainment.killer.domain.value.FinishVoteEvent;
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
    private final RoundProcessor roundProcessor;

    public Game(final List<Player> players, final EventBus eventBus) {
        this(players,
                new PlayerVoteCollector(players, eventBus),
                new RoundProcessor(),
                eventBus);
    }

    protected Game(final List<Player> players,
                   final PlayerVoteCollector playerVoteCollector,
                   final RoundProcessor roundProcessor,
                   final EventBus eventBus) {
        this.players = players;
        this.voteCollector = playerVoteCollector;
        this.eventBus = eventBus;
        this.roundProcessor = roundProcessor;
        this.eventBus.register(this);
        this.isFinished = new AtomicBoolean(false);
        this.isVoting = new AtomicBoolean(false);
        this.state = STARTED;
        doAndNextStep(null);
    }

    @Subscribe
    private void finishVote(final FinishVoteEvent event) {
        if (isVoting.compareAndSet(true, false)) {
            doAndNextStep(event.getVotePlayerName());
        }
    }

    private void doAndNextStep(final String playerName) {
        voteCollector.clear();
        roundProcessor.process(state, playerName);
        state = state.next();
        isVoting.set(true);
    }

    public void finish() {
        if (isFinished.compareAndSet(false, true)) {
            eventBus.post(new FinishGameEvent());
        }
    }
}
