package org.custime.entertainment.killer.domain.model;

import org.assertj.core.util.Lists;
import org.custime.entertainment.killer.domain.value.PlayerVoteEvent;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerVoteCollectorTest {

    @Test
    public void testFinish() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = Utils.getPlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player, "playName"));
        assertThat(playerVoteCollector.isFinished(), is(true));
    }

    @Test
    public void testGetVote() {
        Player player1 = Utils.getPlayer();
        Player player2 = Utils.getPlayer();
        Player player3 = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector
                = Utils.getPlayerVoteCollector(Lists.newArrayList(player1, player2, player3));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player1, "play1"));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player1, "play2"));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player2, "play1"));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player2, "play2"));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player3, "play1"));
        assertThat(playerVoteCollector.getVote(), is("play2"));
    }

    @Test
    public void testClear() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = Utils.getPlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(new PlayerVoteEvent(player, "playName"));
        assertThat(playerVoteCollector.isFinished(), is(true));
        playerVoteCollector.clear();
        assertThat(playerVoteCollector.isFinished(), is(false));
    }
}
