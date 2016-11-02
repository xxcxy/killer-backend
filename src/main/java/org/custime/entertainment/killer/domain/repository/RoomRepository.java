package org.custime.entertainment.killer.domain.repository;

public final class RoomRepository extends AbstractRepository {
    private static final RoomRepository INSTANCE = new RoomRepository();

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return INSTANCE;
    }
}
