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
            return SEER_ASK_TIME;
        }
    }, SEER_ASK_TIME {
        public GameState next() {
            return SEER_ANSWER_TIME;
        }
    }, SEER_ANSWER_TIME {
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
