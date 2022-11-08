package org.csu.persistence;

import org.csu.VO.Room;
import org.springframework.data.repository.Repository;
import java.util.List;

@org.springframework.stereotype.Repository
public interface RoomRepository extends Repository<Room, Integer> {
    List<Room> getRoomsByRoomIdAndAvailable(Integer roomId, Boolean available);
}
