package org.custime.entertainment.killer.domain.model;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerVoteCollector {

    private final ConcurrentHashMap<Player, String> voteMap;
    private final List<Player> players;

    public PlayerVoteCollector(final List<Player> players) {
        this.players = players;
        this.voteMap = new ConcurrentHashMap<>();
    }

    public void collectVote(final Player player, final String playerName) {
        voteMap.put(player, playerName);
    }

    public boolean isFinished() {
        return players.stream().allMatch(player -> voteMap.containsKey(player));
    }

    public void clear() {
        voteMap.clear();
    }
}
