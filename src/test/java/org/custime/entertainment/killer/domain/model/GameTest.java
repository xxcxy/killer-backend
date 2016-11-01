package org.custime.entertainment.killer.domain.model;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.event.ChangeGameStateEvent;
import org.custime.entertainment.killer.domain.event.FinishGameEvent;
import org.custime.entertainment.killer.domain.event.FinishVoteEvent;
import org.custime.entertainment.killer.domain.event.KillPlayerEvent;
import org.custime.entertainment.killer.domain.event.VotePlayerEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.custime.entertainment.killer.domain.value.Role.HUNTER;
import static org.custime.entertainment.killer.domain.value.Role.SEER;
import static org.custime.entertainment.killer.domain.value.Role.VILLAGER;
import static org.custime.entertainment.killer.domain.value.Role.WEREWOLF;
import static org.custime.entertainment.killer.domain.value.Role.WITCH;
import static org.hamcrest.CoreMatchers.hasItem;
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

    private void assembly(final EventBus eventBus, final Player... player) {
        Utils.getGame(Lists.newArrayList(player),
                mock(PlayerVoteCollector.class),
                mock(RoundProcessor.class),
                eventBus);
    }

    @Test
    public void testGameStateChanged() {
        EventBus eventBus = spy(new EventBus());
        ArgumentCaptor<ChangeGameStateEvent> argumentCaptor = ArgumentCaptor.forClass(ChangeGameStateEvent.class);
        assembly(eventBus, mock(Player.class));
        eventBus.post(new FinishVoteEvent(null));
        verify(eventBus, atLeastOnce()).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues(),
                hasItems(isA(ChangeGameStateEvent.class)));
    }

    @Test
    public void testUpdatePlayerStateWhenVoteFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        player.setRole(WEREWOLF);
        Player player1 = Utils.getPlayer(eventBus, "playName1");
        player1.setRole(WEREWOLF);
        Player hunter = Utils.getPlayer(eventBus, "hunter");
        hunter.setRole(HUNTER);
        Player villager = Utils.getPlayer(eventBus, "villager");
        villager.setRole(VILLAGER);
        assembly(eventBus, player, player1, hunter, villager);
        eventBus.post(new VotePlayerEvent("playerName"));
        assertThat(player1.isDeath(), is(false));
        assertThat(player.isDeath(), is(true));
    }

    @Test
    public void testUpdatePlayerStateWhenKillFinish() {
        EventBus eventBus = new EventBus();
        Player player = Utils.getPlayer(eventBus);
        player.setRole(WEREWOLF);
        Player player1 = Utils.getPlayer(eventBus, "playName1");
        player1.setRole(WEREWOLF);
        Player hunter = Utils.getPlayer(eventBus, "hunter");
        hunter.setRole(HUNTER);
        Player villager = Utils.getPlayer(eventBus, "villager");
        villager.setRole(VILLAGER);
        assembly(eventBus, player, player1, hunter, villager);
        eventBus.post(new KillPlayerEvent("playerName"));
        assertThat(player1.isDeath(), is(false));
        assertThat(player.isDeath(), is(true));
    }

    @Test
    public void testGameFinishWhenAllWerewolvesDie() {
        EventBus eventBus = spy(new EventBus());
        assembleAllRoleGame(eventBus);
        eventBus.post(new KillPlayerEvent("werewolf"));
        verifyFinishGameEvent(eventBus);
    }

    private void verifyFinishGameEvent(final EventBus eventBus) {
        ArgumentCaptor<FinishGameEvent> argumentCaptor = ArgumentCaptor.forClass(FinishGameEvent.class);
        verify(eventBus, atLeastOnce()).post(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues(), hasItem(isA(FinishGameEvent.class)));
    }

    private void assembleAllRoleGame(final EventBus eventBus) {
        Player werewolf = Utils.getPlayer(eventBus, "werewolf");
        werewolf.setRole(WEREWOLF);
        Player villager = Utils.getPlayer(eventBus, "villager");
        villager.setRole(VILLAGER);
        Player seer = Utils.getPlayer(eventBus, "seer");
        seer.setRole(SEER);
        Player witch = Utils.getPlayer(eventBus, "witch");
        witch.setRole(WITCH);
        Player hunter = Utils.getPlayer(eventBus, "hunter");
        hunter.setRole(HUNTER);
        assembly(eventBus, werewolf, villager, seer, witch, hunter);
    }

    @Test
    public void testGameFinishWhenAllVillagerDie() {
        EventBus eventBus = spy(new EventBus());
        assembleAllRoleGame(eventBus);
        eventBus.post(new KillPlayerEvent("villager"));
        verifyFinishGameEvent(eventBus);
    }

    @Test
    public void testGameFinishWhenAllSpecialVillagerDie() {
        EventBus eventBus = spy(new EventBus());
        assembleAllRoleGame(eventBus);
        eventBus.post(new KillPlayerEvent("seer"));
        eventBus.post(new KillPlayerEvent("witch"));
        eventBus.post(new KillPlayerEvent("hunter"));
        verifyFinishGameEvent(eventBus);
    }
}
