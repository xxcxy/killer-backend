package org.custime.entertainment.killer.domain.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void testVoteCollectedWhenPlayerVote() {
        PlayerVoteCollector collector = mock(PlayerVoteCollector.class);
        when(collector.isFinished()).thenReturn(true);
        Player player = mock(Player.class);
        ArgumentCaptor<BiConsumer> captor = ArgumentCaptor.forClass(BiConsumer.class);
        game = Utils.getGame(player, collector);
        verify(player).setVoteConsumer(captor.capture());
        captor.getValue().accept(player, "playerName");
        verify(collector).collectVote(player, "playerName");
    }
}
