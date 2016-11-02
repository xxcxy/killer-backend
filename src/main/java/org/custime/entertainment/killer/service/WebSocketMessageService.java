package org.custime.entertainment.killer.service;

import org.custime.entertainment.killer.domain.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

public class WebSocketMessageService implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageService.class);

    private final WebSocketSession webSocketSession;

    public WebSocketMessageService(final WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    @Override
    public void updatePlayersState(final Map<String, Boolean> stateMap) {
        try {
            webSocketSession.sendMessage(new TextMessage(""));
        } catch (IOException e) {
            LOGGER.error("send player state error:", e);
        }
    }
}
