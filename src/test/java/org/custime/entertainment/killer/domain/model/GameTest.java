package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Test
    public void testVoteCollectedWhenPlayerVote() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        PlayerVoteCollector collector = Utils.getPlayerVoteCollector(Lists.newArrayList(player), eventBus);
        RoundProcessor roundProcessor = mock(RoundProcessor.class);
        Utils.getGame(player, collector, roundProcessor, eventBus);
        player.vote("playerName");
        verify(roundProcessor, times(2)).process(any(), any());
    }
}
