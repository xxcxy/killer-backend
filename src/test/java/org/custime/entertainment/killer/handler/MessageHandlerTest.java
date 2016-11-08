package org.custime.entertainment.killer.handler;

import org.custime.entertainment.killer.domain.factory.RoomFactory;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.model.Room;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.custime.entertainment.killer.domain.repository.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MessageHandlerTest {

    private PlayerRepository playerRepository;
    private RoomFactory roomFactory;
    private RoomRepository roomRepository;
    private WebSocketSession session;
    private MessageHandler messageHandler;

    @Before
    public void setup() {
        playerRepository = PlayerRepository.getInstance();
        roomFactory = mock(RoomFactory.class);
        roomRepository = RoomRepository.getInstance();
        session = mock(WebSocketSession.class);
        when(session.getId()).thenReturn("sessionId");
        playerRepository.remove("sessionId");
        roomRepository.remove("roomName");
        messageHandler = new MessageHandler(playerRepository, roomFactory, roomRepository);
    }

    @Test
    public void testIllegalMessage() throws IOException {
        messageHandler.handle(session, new TextMessage("illegal"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testSendMessageError() throws IOException {
        doThrow(new IOException()).when(session).sendMessage(any());
        messageHandler.handle(session, new TextMessage("illegal"));
    }

    @Test
    public void testEmptyPlayer() throws IOException {
        messageHandler.handle(session, getMessage("createRoom", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testNotExistedType() throws IOException {
        playerRepository.save("sessionId", mock(Player.class));
        messageHandler.handle(session, getMessage("type", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testCreateRoom() {
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        when(roomFactory.createRoom()).thenReturn(room);
        messageHandler.handle(session, getMessage("createRoom", "roomName"));
        verify(room).addPlayer(any());
        assertThat(roomRepository.getOne("roomName").isPresent(), is(true));
    }

    @Test
    public void testCreateExistedRoom() throws IOException {
        roomRepository.save("roomExisted", mock(Room.class));
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        when(roomFactory.createRoom()).thenReturn(room);
        messageHandler.handle(session, getMessage("createRoom", "roomExisted"));
        verify(room, never()).addPlayer(any());
        verify(session).sendMessage(any());
    }

    @Test
    public void testAddRoom() {
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        roomRepository.save("roomName", room);
        messageHandler.handle(session, getMessage("addRoom", "roomName"));
        verify(room).addPlayer(any());
    }

    @Test
    public void testAddNotExistedRoom() throws IOException {
        playerRepository.save("sessionId", mock(Player.class));
        messageHandler.handle(session, getMessage("addRoom", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testAddStartedRoom() throws IOException {
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        when(room.addPlayer(any())).thenReturn(false);
        roomRepository.save("roomName", room);
        messageHandler.handle(session, getMessage("addRoom", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testLeaveRoom() {
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        roomRepository.save("roomName", room);
        messageHandler.handle(session, getMessage("leaveRoom", "roomName"));
        verify(room).removePlayer(any());
    }

    @Test
    public void testLeaveNotExistedRoom() throws IOException {
        playerRepository.save("sessionId", mock(Player.class));
        messageHandler.handle(session, getMessage("leaveRoom", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testLeaveStartedRoom() throws IOException {
        playerRepository.save("sessionId", mock(Player.class));
        Room room = mock(Room.class);
        when(room.removePlayer(any())).thenReturn(false);
        roomRepository.save("roomName", room);
        messageHandler.handle(session, getMessage("leaveRoom", "roomName"));
        verify(session).sendMessage(any());
    }

    @Test
    public void testStartGame() {
        playerRepository.save("sessionId", mock(Player.class));
        messageHandler.handle(session, getMessage("startGame", "roomName"));
    }

    private TextMessage getMessage(final String type, final String roomName) {
        return new TextMessage("{\"type\":\"" + type + "\",\"roomName\":\"" + roomName + "\"}");
    }
}
