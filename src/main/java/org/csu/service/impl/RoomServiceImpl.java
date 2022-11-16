package org.csu.service.impl;

import org.csu.VO.Reserve;
import org.csu.VO.Room;
import org.csu.common.CommonResponse;
import org.csu.persistence.ReserveJPA;
import org.csu.persistence.RoomJPA;
import org.csu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomJPA roomJPA;
    @Autowired
    ReserveJPA reserveJPA;

    /**
     * @param roomName 房间的名字
     * @param available 房间是否可用
     * @Description: 加入新的房间
     * @return CommonResponse<Room>
     */
    @Override
    public CommonResponse<Room> insertRoom(String roomName, Boolean available) {
        //        当前房间不存在
        if (roomJPA.findByNameAndDelFlagEquals(roomName, Boolean.TRUE) == null){
            Room room = new Room();
            room.setName(roomName);
            room.setAvailable(available);
            return CommonResponse.createForSuccess(roomJPA.save(room));
        }
        return CommonResponse.createForError("该房间\""+roomName+"\"已存在");
    }

    /**
     * @param roomName 房间的名字
     * @Description: 将room的有效位和相关的reserve的有效位置为false
     * @return CommonResponse<Room>
     */
    @Override
    public CommonResponse<Room> deleteRoom(String roomName) {
        Room room = roomJPA.findByNameAndDelFlagEquals(roomName, Boolean.TRUE);
//        如果房间存在
        if (room != null){
            room.setDelFlag(Boolean.FALSE);
            roomJPA.save(room);
            setRelevantReservesFlagFalse(room);
            return CommonResponse.createForSuccess(room);
        }
        return CommonResponse.createForError("当前要删除的房间\""+roomName+"\"不存在");
    }

    /**
     * @param roomName 房间的名字
     * @param available 房间是否有效
     * @Description: 更新room的available状态，若room的available状态为false，使相关的订单失效
     * @return CommonResponse<Room>
     */
    @Override
    public CommonResponse<Room> updateRoom(String roomName, Boolean available) {
        //        当前房间存在
        Room room = roomJPA.findByNameAndDelFlagEquals(roomName, Boolean.TRUE);
        if (room != null){
            room.setAvailable(available);
//        JPA.save方法可以执行添加也可以执行更新，如果存在主键就执行更新，不存在就添加数据
            roomJPA.save(room);
            if (available == Boolean.FALSE){
                setRelevantReservesFlagFalse(room);
            }
            return CommonResponse.createForSuccess(room);
        }
        return null;
    }

    /**
     * @param roomName 房间的名字
     * @Description: 模糊查询所有的room
     * @return CommonResponse<List<Room>>
     */
    @Override
    public CommonResponse<List<Room>> selectRooms(String roomName) {
        return CommonResponse.createForSuccess(roomJPA.findRoomsByNameLikeAndDelFlagEquals("%"+roomName+"%", Boolean.TRUE));
    }

    private void setRelevantReservesFlagFalse(Room room){
        List<Reserve> reserveList = reserveJPA.findReservesByRoomIdAndDelFlagEquals(room.getRoomId(), Boolean.TRUE);
        if (!reserveList.isEmpty()) {
            for (Reserve reserve : reserveList){
                reserve.setDelFlag(Boolean.FALSE);
                reserveJPA.save(reserve);
            }
        }
    }
}
