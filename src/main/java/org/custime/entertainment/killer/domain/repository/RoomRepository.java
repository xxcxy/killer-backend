package org.custime.entertainment.killer.domain.repository;

import org.custime.entertainment.killer.domain.model.Room;

public final class RoomRepository extends AbstractRepository<Room> {
    private static final RoomRepository INSTANCE = new RoomRepository();

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return INSTANCE;
    }
}
