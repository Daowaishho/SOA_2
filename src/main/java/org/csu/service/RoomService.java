package org.csu.service;

import org.csu.VO.Room;

import java.util.List;

public interface RoomService {
    Room insertRoom(String roomName, Boolean available);
    void deleteRoom(String roomName);
    Room updateRoom(String roomName, Boolean available);
    List<Room> selectRooms(String roomName);
}
