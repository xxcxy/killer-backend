package org.custime.entertainment.killer.handler;

import org.custime.entertainment.killer.domain.factory.PlayerFactory;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.socket.CloseStatus.NORMAL;

public class ConnectionHandlerTest {

    private final PlayerFactory playerFactory = mock(PlayerFactory.class);
    private final PlayerRepository playerRepository = PlayerRepository.getInstance();
    private final MessageHandler messageHandler = mock(MessageHandler.class);

    @Test
    public void testHandleMessage() {
        ConnectionHandler connectionHandler = new ConnectionHandler(messageHandler, playerFactory, playerRepository);
        connectionHandler.handleTextMessage(mock(WebSocketSession.class), new TextMessage(""));
        verify(messageHandler).handle(any(), any());
    }

    @Test
    public void testAfterConnectionEstablished() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(playerFactory.createPlayer(any())).thenReturn(mock(Player.class));
        when(session.getId()).thenReturn("sessionId");
        ConnectionHandler connectionHandler = new ConnectionHandler(messageHandler, playerFactory, playerRepository);
        connectionHandler.afterConnectionEstablished(session);
        verify(playerFactory).createPlayer(any());
        verify(session).getId();
    }

    @Test
    public void testAfterConnectionClosed() throws Exception {
        WebSocketSession session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("sessionId");
        Player player = mock(Player.class);
        playerRepository.save("sessionId", player);
        ConnectionHandler connectionHandler = new ConnectionHandler(messageHandler, playerFactory, playerRepository);
        connectionHandler.afterConnectionClosed(session, NORMAL);
        verify(player).offline();
        assertThat(playerRepository.getOne("sessionId").isPresent(), is(false));
    }
}
