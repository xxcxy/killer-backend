package org.custime.entertainment.killer.domain.event;

public class AnswerProphetEvent {

    private final boolean isKiller;

    public AnswerProphetEvent(final boolean isKiller) {
        this.isKiller = isKiller;
    }

}
