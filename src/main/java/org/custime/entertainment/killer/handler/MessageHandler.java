package org.custime.entertainment.killer.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.custime.entertainment.killer.domain.factory.RoomFactory;
import org.custime.entertainment.killer.domain.model.Player;
import org.custime.entertainment.killer.domain.model.Room;
import org.custime.entertainment.killer.domain.repository.PlayerRepository;
import org.custime.entertainment.killer.domain.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
public class MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandler.class);
    private final ObjectMapper objectMapper;
    private final PlayerRepository playerRepository;
    private final RoomFactory roomFactory;
    private final RoomRepository roomRepository;
    private final Map<String, BiFunction<Player, String, Boolean>> handler;

    @Inject
    public MessageHandler(final PlayerRepository playerRepository,
                          final RoomFactory roomFactory,
                          final RoomRepository roomRepository) {
        objectMapper = new ObjectMapper();
        this.playerRepository = playerRepository;
        this.roomFactory = roomFactory;
        this.roomRepository = roomRepository;
        handler = ImmutableMap.of("createRoom", this::createRoom,
                "addRoom", this::addRoom,
                "leaveRoom", this::leaveRoom,
                "startGame", this::startGame);
    }

    public void handle(final WebSocketSession session, final TextMessage message) {
        try {
            Map<String, String> msg = objectMapper.readValue(message.getPayload(), Map.class);
            Optional<Player> playerOptional = playerRepository.getOne(session.getId());
            if (!playerOptional.isPresent()
                    || !handler.containsKey(msg.get("type"))
                    || !handler.get(msg.get("type")).apply(playerOptional.get(), msg.get("roomName"))) {
                sendMessageSilence(session, new TextMessage(""));
            }
        } catch (IOException e) {
            LOGGER.error("parse text message error:", e);
            sendMessageSilence(session, new TextMessage(""));
        }
    }

    private void sendMessageSilence(final WebSocketSession session, final TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOGGER.error("webSocket send message error:", e);
        }
    }

    private boolean createRoom(final Player player, final String roomName) {
        Room room = roomFactory.createRoom();
        return roomRepository.save(roomName, room) && room.addPlayer(player);
    }

    private boolean addRoom(final Player player, final String roomName) {
        return true;
    }

    private boolean leaveRoom(final Player player, final String roomName) {
        return true;
    }

    private boolean startGame(final Player player, final String roomName) {
        return true;
    }
}
