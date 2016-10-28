package org.custime.entertainment.killer.domain.service;

import java.util.Map;

public interface PlayerService {

    void updatePlayersState(final Map<String, Boolean> stateMap);
}
