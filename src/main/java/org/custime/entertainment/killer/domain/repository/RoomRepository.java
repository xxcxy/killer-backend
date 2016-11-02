package org.custime.entertainment.killer.domain.repository;

import com.google.common.eventbus.EventBus;
import org.custime.entertainment.killer.domain.model.Room;

public final class RoomRepository extends AbstractRepository {
    public Room createRoom() {
        return new Room(new EventBus());
    }

    private static final RoomRepository INSTANCE = new RoomRepository();

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return INSTANCE;
    }
}
