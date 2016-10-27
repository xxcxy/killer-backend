package org.custime.entertainment.killer.domain.model;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.custime.entertainment.killer.domain.value.FinishVoteEvent;
import org.custime.entertainment.killer.domain.value.PlayerVoteEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerVoteCollector {

    private final ConcurrentHashMap<Player, String> voteMap;
    private final EventBus eventBus;
    private final List<Player> players;

    public PlayerVoteCollector(final List<Player> players, final EventBus eventBus) {
        this.players = players;
        this.eventBus = eventBus;
        this.voteMap = new ConcurrentHashMap<>();
        this.eventBus.register(this);
    }

    @Subscribe
    public void collectVote(final PlayerVoteEvent event) {
        if (players.contains(event.getPlayer())) {
            voteMap.put(event.getPlayer(), event.getVotePlayerName());
        }
        if (isFinished()) {
            eventBus.post(new FinishVoteEvent(getVote()));
        }
    }

    public String getVote() {
        return Collections.max(voteMap.values()
                        .stream()
                        .collect(Collectors.groupingBy(String::toString, Collectors.counting())).entrySet(),
                (e1, e2) -> e1.getValue().intValue() - e2.getValue().intValue()).getKey();
    }

    public boolean isFinished() {
        return players.stream().allMatch(player -> voteMap.containsKey(player));
    }

    public void clear() {
        voteMap.clear();
    }
}
