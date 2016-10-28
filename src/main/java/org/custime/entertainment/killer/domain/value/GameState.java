package org.custime.entertainment.killer.domain.value;

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
            return WITCH_SAVE_TIME;
        }
    }, WITCH_SAVE_TIME {
        public GameState next() {
            return WITCH_POISON_TIME;
        }
    }, WITCH_POISON_TIME {
        public GameState next() {
            return PROPHET_ASK_TIME;
        }
    }, PROPHET_ASK_TIME {
        public GameState next() {
            return PROPHET_ANSWER_TIME;
        }
    }, PROPHET_ANSWER_TIME {
        public GameState next() {
            return DAY;
        }
    }, DAY {
        public GameState next() {
            return VOTE;
        }
    }, VOTE {
        public GameState next() {
            return NIGHT;
        }
    };

    public abstract GameState next();
}
