package org.csu.persistence;

import org.csu.VO.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

public interface RoomJPA extends
//        SpringDataJPA提供的简单数据操作接口
        JpaRepository<Room, Integer>,
//SpringDataJPA提供的复杂查询接口
        JpaSpecificationExecutor<Room>,
//  序列化接口
        Serializable {
    Room findByNameAndDelFlagEquals(String roomName, Boolean delFlag);
    List<Room> findRoomsByNameLikeAndDelFlagEquals(String roomName, Boolean delFlag);
    Room findByRoomIdAndDelFlagEquals(Integer roomId, Boolean delFlag);
}
