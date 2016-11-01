package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.value.GameState;
import org.custime.entertainment.killer.domain.value.KillPlayerEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RoundProcessorTest {

    @Test
    public void testProcessVote() {
        EventBus eventBus = mock(EventBus.class);
        RoundProcessor roundProcessor = new RoundProcessor(eventBus);
        roundProcessor.process(GameState.VOTE, "player1");
        verify(eventBus).post(any());
    }

    @Test
    public void testProphetAnswer() {
        EventBus eventBus = mock(EventBus.class);
        RoundProcessor roundProcessor = new RoundProcessor(eventBus);
        roundProcessor.process(GameState.SEER_ANSWER_TIME, "");
        verify(eventBus).post(any());
    }

    @Test
    public void testProcessKill() {
        EventBus eventBus = mock(EventBus.class);
        ArgumentCaptor<KillPlayerEvent> argumentCaptor = ArgumentCaptor.forClass(KillPlayerEvent.class);
        RoundProcessor roundProcessor = new RoundProcessor(eventBus);
        roundProcessor.process(GameState.KILLER_TIME, "play1");
        roundProcessor.process(GameState.DAY, "");
        verify(eventBus).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPlayerNames(), hasItem("play1"));
    }

    @Test
    public void testProcessKillWhenSave() {
        EventBus eventBus = mock(EventBus.class);
        ArgumentCaptor<KillPlayerEvent> argumentCaptor = ArgumentCaptor.forClass(KillPlayerEvent.class);
        RoundProcessor roundProcessor = new RoundProcessor(eventBus);
        roundProcessor.process(GameState.KILLER_TIME, "play1");
        roundProcessor.process(GameState.WITCH_SAVE_TIME, "play1");
        roundProcessor.process(GameState.DAY, "");
        verify(eventBus).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPlayerNames().isEmpty(), is(true));
    }

    @Test
    public void testProcessKillWhenPoison() {
        EventBus eventBus = mock(EventBus.class);
        ArgumentCaptor<KillPlayerEvent> argumentCaptor = ArgumentCaptor.forClass(KillPlayerEvent.class);
        RoundProcessor roundProcessor = new RoundProcessor(eventBus);
        roundProcessor.process(GameState.KILLER_TIME, "play1");
        roundProcessor.process(GameState.WITCH_POISON_TIME, "play2");
        roundProcessor.process(GameState.DAY, "");
        verify(eventBus).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPlayerNames(), hasItem("play1"));
        assertThat(argumentCaptor.getValue().getPlayerNames(), hasItem("play2"));
    }
}
