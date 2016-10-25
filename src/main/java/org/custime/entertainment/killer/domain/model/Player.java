package org.custime.entertainment.killer.domain.model;

import java.util.function.BiConsumer;

public class Player {

    private BiConsumer<Player, String> voteConsumer;

    public void setVoteConsumer(final BiConsumer<Player, String> voteConsumer) {
        this.voteConsumer = voteConsumer;
    }
}
