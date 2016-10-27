package org.custime.entertainment.killer.domain.repository;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.model.Room;

public class RoomRepository {
    public Room createRoom() {
        return new Room(new EventBus());
    }
}
