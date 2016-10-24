package org.custime.entertainment.killer.domain.model;

import java.util.function.Consumer;

public class Game {
    private boolean started;
    private Consumer<Game> finishListener;

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void listenToFinish(final Consumer<Game> consumer) {
        finishListener = consumer;
    }

    public void finish() {
        finishListener.accept(this);
    }
}
