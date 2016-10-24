package org.custime.entertainment.killer.domain.model;

public class Game {
    private boolean started;

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }
}
