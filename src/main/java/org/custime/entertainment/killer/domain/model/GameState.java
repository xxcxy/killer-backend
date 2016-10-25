package org.custime.entertainment.killer.domain.model;

public enum GameState {
    STARTED {
        public GameState next() {
            return NIGHT;
        }
    }, NIGHT {
        public GameState next() {
            return KILLER_TIME;
        }
    }, KILLER_TIME {
        public GameState next() {
            return WITCH_TIME;
        }
    }, WITCH_TIME {
        public GameState next() {
            return PROPHET_TIME;
        }
    }, PROPHET_TIME {
        public GameState next() {
            return DAY;
        }
    }, DAY {
        public GameState next() {
            return NIGHT;
        }
    };

    public abstract GameState next();
}
