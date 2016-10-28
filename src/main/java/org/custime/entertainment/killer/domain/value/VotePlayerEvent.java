package org.custime.entertainment.killer.domain.value;

public class VotePlayerEvent {
    private final String voteName;

    public VotePlayerEvent(final String voteName) {
        this.voteName = voteName;
    }

    public String getVoteName() {
        return voteName;
    }
}
