package org.custime.entertainment.killer.domain;

public class Game {
    private boolean started;

    public void start() {
        started = true;
    }

    public boolean isStarted() {
        return started;
    }
}
