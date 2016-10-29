package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.value.FinishGameEvent;
import org.custime.entertainment.killer.domain.value.FinishVoteEvent;
import org.custime.entertainment.killer.domain.value.GameState;
import org.custime.entertainment.killer.domain.value.KillPlayerEvent;
import org.custime.entertainment.killer.domain.value.UpdatePlayerStateEvent;
import org.custime.entertainment.killer.domain.value.VotePlayerEvent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.custime.entertainment.killer.domain.value.GameState.STARTED;

public class Game {
    private final List<Player> players;
    private final EventBus eventBus;
    private GameState state;
    private final AtomicBoolean isFinished;
    private final PlayerVoteCollector voteCollector;
    private final RoundProcessor roundProcessor;

    public Game(final List<Player> players, final EventBus eventBus) {
        this(players,
                new PlayerVoteCollector(players, eventBus),
                new RoundProcessor(eventBus),
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
        this.isFinished = new AtomicBoolean(false);
        this.state = STARTED;
        finishVote(new FinishVoteEvent(null));
    }

    @Subscribe
    private void finishVote(final FinishVoteEvent event) {
        roundProcessor.process(state, event.getVotePlayerName());
        state = state.next();
        voteCollector.startVote();
    }

    @Subscribe
    private void votePlayer(final VotePlayerEvent event) {
        updatePlayerState(Lists.newArrayList(event.getVoteName()));
    }

    @Subscribe
    private void killPlayer(final KillPlayerEvent event) {
        updatePlayerState(event.getPlayerNames());
    }

    private void updatePlayerState(final Collection<String> playerNames) {
        players.stream().filter(player -> playerNames.contains(player.getName())).forEach(player -> player.die());
        if (!maybeFinish()) {
            eventBus.post(new UpdatePlayerStateEvent(players.stream()
                    .collect(Collectors.toMap(player -> player.getName(), player -> player.isDeath()))));
        }
    }

    private boolean maybeFinish() {
        if (checkFinish() && isFinished.compareAndSet(false, true)) {
            eventBus.post(new FinishGameEvent());
            return true;
        }
        return false;
    }

    private boolean checkFinish() {
        return players.stream().allMatch(player -> player.isDeath());
    }
}
