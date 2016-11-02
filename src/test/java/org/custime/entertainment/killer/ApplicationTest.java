package org.custime.entertainment.killer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testWebSocketConnection() throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = client.doHandshake(new TextWebSocketHandler(),
                new StringBuilder("ws://localhost:").append(port).append("/werewolf").toString()).get();
        session.sendMessage(new TextMessage("test"));
        session.close();
    }
}
