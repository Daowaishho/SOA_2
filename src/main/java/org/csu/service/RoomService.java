package org.csu.service;

import org.csu.VO.Reserve;
import org.csu.VO.Room;
import org.csu.common.CommonResponse;

import java.util.List;

public interface RoomService {
    CommonResponse<Room> insertRoom(String roomName, Boolean available);
    CommonResponse<Room> deleteRoom(String roomName);
    CommonResponse<Room> updateRoom(String roomName, Boolean available);
    CommonResponse<List<Room>> selectRooms(String roomName);
}
