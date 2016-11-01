package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.value.ChangeGameStateEvent;
import org.custime.entertainment.killer.domain.value.FinishVoteEvent;
import org.custime.entertainment.killer.domain.value.KillPlayerEvent;
import org.custime.entertainment.killer.domain.value.VotePlayerEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
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

    @Test
    public void testGameStateChanged() {
        EventBus eventBus = spy(new EventBus());
        ArgumentCaptor<ChangeGameStateEvent> argumentCaptor = ArgumentCaptor.forClass(ChangeGameStateEvent.class);
        Utils.getGame(mock(Player.class), mock(PlayerVoteCollector.class), mock(RoundProcessor.class), eventBus);
        eventBus.post(new FinishVoteEvent(null));
        verify(eventBus, atLeastOnce()).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues(),
                hasItems(isA(ChangeGameStateEvent.class)));
    }

    @Test
    public void testUpdatePlayerStateWhenVoteFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        Player player1 = Utils.getPlayer(eventBus, "playName1");
        Utils.getGame(Lists.newArrayList(player, player1),
                mock(PlayerVoteCollector.class),
                mock(RoundProcessor.class),
                eventBus);
        eventBus.post(new VotePlayerEvent("playerName"));
        assertThat(player1.isDeath(), is(false));
        assertThat(player.isDeath(), is(true));
    }

    @Test
    public void testUpdatePlayerStateWhenKillFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        Player player1 = Utils.getPlayer(eventBus, "playName1");
        Utils.getGame(Lists.newArrayList(player, player1),
                mock(PlayerVoteCollector.class),
                mock(RoundProcessor.class),
                eventBus);
        eventBus.post(new KillPlayerEvent("playerName"));
        assertThat(player1.isDeath(), is(false));
        assertThat(player.isDeath(), is(true));
    }

    @Test
    public void testGameFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        Player player1 = Utils.getPlayer(eventBus, "playerName1");
        Utils.getGame(Lists.newArrayList(player, player1),
                mock(PlayerVoteCollector.class),
                mock(RoundProcessor.class),
                eventBus);
        eventBus.post(new KillPlayerEvent("playerName"));
        eventBus.post(new KillPlayerEvent("playerName1"));
        assertThat(player1.isDeath(), is(false));
        assertThat(player.isDeath(), is(false));
    }
}
