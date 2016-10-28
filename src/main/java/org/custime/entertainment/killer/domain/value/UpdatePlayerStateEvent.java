package org.custime.entertainment.killer.domain.value;

import java.util.Map;

public class UpdatePlayerStateEvent {
    private final Map<String, Boolean> stateMap;

    public UpdatePlayerStateEvent(final Map<String, Boolean> stateMap) {
        this.stateMap = stateMap;
    }

    public Map<String, Boolean> getStateMap() {
        return stateMap;
    }
}
