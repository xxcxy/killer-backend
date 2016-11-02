package org.custime.entertainment.killer.domain.factory;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.model.Room;

public class RoomFactory {

    public Room createRoom() {
        return new Room(new EventBus());
    }
}
