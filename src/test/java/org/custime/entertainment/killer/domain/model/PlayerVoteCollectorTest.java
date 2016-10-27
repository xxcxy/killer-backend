package org.custime.entertainment.killer.domain.model;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerVoteCollectorTest {

    @Test
    public void testFinish() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = Utils.getPlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(player, "playName");
        assertThat(playerVoteCollector.isFinished(), is(true));
    }

    @Test
    public void testGetVote() {
        Player player1 = Utils.getPlayer();
        Player player2 = Utils.getPlayer();
        Player player3 = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector
                = Utils.getPlayerVoteCollector(Lists.newArrayList(player1, player2, player3));
        playerVoteCollector.collectVote(player1, "play1");
        playerVoteCollector.collectVote(player1, "play2");
        playerVoteCollector.collectVote(player2, "play1");
        playerVoteCollector.collectVote(player2, "play2");
        playerVoteCollector.collectVote(player3, "play1");
        assertThat(playerVoteCollector.getVote(), is("play2"));
    }

    @Test
    public void testClear() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = Utils.getPlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(player, "playName");
        assertThat(playerVoteCollector.isFinished(), is(true));
        playerVoteCollector.clear();
        assertThat(playerVoteCollector.isFinished(), is(false));
    }
}
