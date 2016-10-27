package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.junit.Test;

import static org.custime.entertainment.killer.domain.value.GameState.NIGHT;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayerVoteCollectorTest {

    @Test
    public void testFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        PlayerVoteCollector collector = Utils.getPlayerVoteCollector(Lists.newArrayList(player), eventBus);
        RoundProcessor roundProcessor = mock(RoundProcessor.class);
        Utils.getGame(player, collector, roundProcessor, eventBus);
        player.vote("playerName");
        verify(roundProcessor, times(2)).process(any(), any());
    }

    @Test
    public void testGetVote() {
        EventBus eventBus = new EventBus();
        Player player1 = Utils.getPlayer(eventBus);
        Player player2 = Utils.getPlayer(eventBus);
        Player player3 = Utils.getPlayer(eventBus);
        PlayerVoteCollector playerVoteCollector
                = Utils.getPlayerVoteCollector(Lists.newArrayList(player1, player2, player3), eventBus);
        RoundProcessor roundProcessor = mock(RoundProcessor.class);
        Utils.getGame(Lists.newArrayList(player1, player2, player3), playerVoteCollector, roundProcessor, eventBus);
        player1.vote("play1");
        player1.vote("play2");
        player2.vote("play1");
        player2.vote("play2");
        player3.vote("play1");
        verify(roundProcessor).process(NIGHT, "play2");
    }
}
