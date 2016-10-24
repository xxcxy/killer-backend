package org.custime.entertainment.killer.domain.model;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameTest {
    private Game game;

    @Before
    public void setup() {
        game = Utils.getGame();
    }

    @Test
    public void testListenToGameFinish() {
        Consumer consumer = mock(Consumer.class);
        game.listenToFinish(consumer);
        game.finish();
        verify(consumer).accept(game);
    }

    @Test
    public void testGameFinishWithNullFinishListener() {
        game.finish();
    }

}
