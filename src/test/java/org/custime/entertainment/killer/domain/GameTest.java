package org.custime.entertainment.killer.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Game game = new Game();

    @Test
    public void testGameStart() {
        assertThat(game.isStarted(), is(false));
        game.start();
        assertThat(game.isStarted(), is(true));
    }
}
