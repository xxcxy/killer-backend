package org.custime.entertainment.killer.handler;

import org.custime.entertainment.killer.domain.factory.PlayerFactory;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.custime.entertainment.killer.service.WebSocketMessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.inject.Inject;

@Component
public class ConnectionHandler extends TextWebSocketHandler {

    private final PlayerFactory playerFactory;
    private final PlayerRepository playerRepository;
    private final MessageHandler messageHandler;

    @Inject
    public ConnectionHandler(final MessageHandler messageHandler) {
        this.playerFactory = new PlayerFactory();
        this.playerRepository = PlayerRepository.getInstance();
        this.messageHandler = messageHandler;
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        messageHandler.handle(session.getId(), message);
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        Player player = playerFactory.createPlayer(new WebSocketMessageService(session));
        playerRepository.save(session.getId(), player);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        playerRepository.getOne(session.getId()).ifPresent(player -> player.offline());
        playerRepository.remove(session.getId());
    }
}
