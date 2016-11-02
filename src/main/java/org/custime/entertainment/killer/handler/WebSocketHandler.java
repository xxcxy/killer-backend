package org.custime.entertainment.killer.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message) {
        System.out.println("session id:" + session.getId());
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        System.out.println("connect finish:" + session.getId());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        System.out.println("connect close:" + session.getId());
    }
}
