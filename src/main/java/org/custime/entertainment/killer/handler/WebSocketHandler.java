package org.custime.entertainment.killer.handler;

import org.custime.entertainment.killer.domain.factory.PlayerFactory;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.custime.entertainment.killer.service.WebSocketMessageService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    private final PlayerFactory playerFactory = new PlayerFactory();
    private final PlayerRepository playerRepository = PlayerRepository.getInstance();

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        System.out.println("session id:" + session.getId());
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        Player player = playerFactory.createPlayer(new WebSocketMessageService(session));
        playerRepository.save(session.getId(), player);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        System.out.println("close session id:" + session.getId());
    }
}
