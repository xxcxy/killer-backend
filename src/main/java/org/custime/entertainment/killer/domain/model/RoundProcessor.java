package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.event.AnswerProphetEvent;
import org.custime.entertainment.killer.domain.value.GameState;
import org.custime.entertainment.killer.domain.event.KillPlayerEvent;
import org.custime.entertainment.killer.domain.event.VotePlayerEvent;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.custime.entertainment.killer.domain.value.GameState.DAY;
import static org.custime.entertainment.killer.domain.value.GameState.KILLER_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.SEER_ANSWER_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.VOTE;
import static org.custime.entertainment.killer.domain.value.GameState.WITCH_POISON_TIME;
import static org.custime.entertainment.killer.domain.value.GameState.WITCH_SAVE_TIME;

public class RoundProcessor {

    private final EventBus eventBus;
    private final Map<GameState, Consumer<String>> consumeVote;
    private final AtomicReference<String> killed;
    private final AtomicReference<String> saved;
    private final AtomicReference<String> poisoned;

    public RoundProcessor(final EventBus eventBus) {
        this.eventBus = eventBus;
        this.killed = new AtomicReference<>(null);
        this.saved = new AtomicReference<>(null);
        this.poisoned = new AtomicReference<>(null);
        consumeVote = new ImmutableMap.Builder<GameState, Consumer<String>>()
                .put(KILLER_TIME, playerName -> killed.set(playerName))
                .put(WITCH_SAVE_TIME, playerName -> saved.set(playerName))
                .put(WITCH_POISON_TIME, playerName -> poisoned.set(playerName))
                .put(SEER_ANSWER_TIME, playerName -> eventBus.post(new AnswerProphetEvent(true)))
                .put(DAY, this::notifyKilledPlayer)
                .put(VOTE, playerName -> eventBus.post(new VotePlayerEvent(playerName)))
                .build();
    }

    private void notifyKilledPlayer(final String playerName) {
        eventBus.post(createKillPlayerEvent());
    }

    private KillPlayerEvent createKillPlayerEvent() {
        KillPlayerEvent result = new KillPlayerEvent();
        result.addPlayer(killed.get());
        result.addPlayer(poisoned.get());
        result.removePlayer(saved.get());
        clearNightSet();
        return result;
    }

    private void clearNightSet() {
        killed.set(null);
        saved.set(null);
        poisoned.set(null);
    }

    public void process(final GameState gameState, final String votePlayerName) {
        consumeVote.getOrDefault(gameState, str -> {
        }).accept(votePlayerName);
    }
}
