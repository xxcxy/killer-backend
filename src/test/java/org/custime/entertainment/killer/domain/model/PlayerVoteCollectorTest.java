package org.custime.entertainment.killer.domain.model;

import org.assertj.core.util.Lists;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerVoteCollectorTest {

    @Test
    public void testVote() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = new PlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(player, "playName");
        assertThat(playerVoteCollector.isFinished(), is(true));
    }

    @Test
    public void testClear() {
        Player player = Utils.getPlayer();
        PlayerVoteCollector playerVoteCollector = new PlayerVoteCollector(Lists.newArrayList(player));
        playerVoteCollector.collectVote(player, "playName");
        assertThat(playerVoteCollector.isFinished(), is(true));
        playerVoteCollector.clear();
        assertThat(playerVoteCollector.isFinished(), is(false));
    }
}
