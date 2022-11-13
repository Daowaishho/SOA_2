package org.csu.service.impl;

import org.csu.VO.Room;
import org.csu.persistence.RoomJPA;
import org.csu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomJPA roomJPA;

    @Override
    public Room insertRoom(String roomName, Boolean available) {
        //        当前房间不存在
        if (roomJPA.findByName(roomName) == null){
            Room room = new Room();
            room.setName(roomName);
            room.setAvailable(available);
            return roomJPA.save(room);
        }
        return null;
    }

    @Override
    public void deleteRoom(String roomName) {
        Room room = roomJPA.findByName(roomName);
        if (room != null){
            roomJPA.delete(room);
        }
    }

    @Override
    public Room updateRoom(String roomName, Boolean available) {
        //        当前房间存在
        Room room = roomJPA.findByName(roomName);
        if (room != null){
            Room newRoom = new Room();
            newRoom.setRoomId(room.getRoomId());
            newRoom.setName(roomName);
            newRoom.setAvailable(available);
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            return roomJPA.save(newRoom);
        }
        return null;
    }

    @Override
    public List<Room> selectRooms(String roomName) {
        return roomJPA.findRoomsByNameLike("%"+roomName+"%");
    }
}
