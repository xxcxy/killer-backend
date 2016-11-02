package org.custime.entertainment.killer.service;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebSocketMessageServiceTest {

    @Test
    public void testSendUpdateStateMessage() throws IOException {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        WebSocketMessageService webSocketMessageService = new WebSocketMessageService(webSocketSession);
        webSocketMessageService.updatePlayersState(ImmutableMap.of("villager1", false));
        verify(webSocketSession).sendMessage(any());
    }

    @Test
    public void testSendUpdateStateMessageErrorSilence() throws IOException {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        doThrow(new IOException("send error")).when(webSocketSession).sendMessage(any());
        WebSocketMessageService webSocketMessageService = new WebSocketMessageService(webSocketSession);
        webSocketMessageService.updatePlayersState(ImmutableMap.of("villager1", false));
    }
}
