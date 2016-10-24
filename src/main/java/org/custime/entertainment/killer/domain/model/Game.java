package org.custime.entertainment.killer.domain.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Game {
    private Consumer<Game> finishListener;
    private final List<Player> players;
    private final AtomicBoolean isFinished;

    public Game(final List<Player> players) {
        this.players = players;
        this.isFinished = new AtomicBoolean(false);
    }

    public void listenToFinish(final Consumer<Game> consumer) {
        finishListener = consumer;
    }

    public void finish() {
        if (isFinished.compareAndSet(false, true)) {
            emitFinishListener();
        }
    }

    private void emitFinishListener() {
        if (finishListener != null) {
            finishListener.accept(this);
        }
    }
}
