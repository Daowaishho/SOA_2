package org.csu.service.impl;

import org.csu.VO.Reserve;
import org.csu.VO.Room;
import org.csu.persistence.OperatorJPA;
import org.csu.persistence.ReserveJPA;
import org.csu.persistence.RoomJPA;
import org.csu.service.OperatorService;
import org.csu.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    OperatorJPA operatorJPA;
    @Autowired
    RoomJPA roomJPA;
    @Autowired
    ReserveJPA reserveJPA;

    /**
     * @param operatorName 已存在的预约者的名字
     * @param roomName 已存在的房间的名字
     * @param beginTime 开始的时间
     * @param endTime 结束的时间
     * @Description: 预约者预约房间的一段时间，由开始时间和结束时间确定
     * @return Reserve
     */
    @Override
    public Reserve insertReserve(String operatorName, String roomName, Date beginTime, Date endTime) {
//        用户和房间都存在且该房间beginTime到endTime之间的时间段是否没有冲突
        if (checkOperatorExist(operatorName) &&
                checkRoomExist(roomName) &&
                checkConflictsExist(roomName, beginTime, endTime)){
//          插入新的预约
            Reserve reserve = new Reserve();
            reserve.setOperatorId(getOperatorIdbyOperatorName(operatorName));
            reserve.setRoomId(getRoomIdbyRoomName(roomName));
            reserve.setBeginTime(beginTime);
            reserve.setEndTime(endTime);
            return reserveJPA.save(reserve);
        }
        return null;
    }

    @Override
    public void deleteReserve(Integer reserveId) {

    }

    @Override
    public Reserve updateReserve(Integer reserveId, String operatorName, String roomName, Date beginTime, Date endTime) {
        return null;
    }

    @Override
    public List<Reserve> selectReserves(String operatorName, String roomName, Date beginTime, Date endTime) {
        return null;
    }

    /**
     * @param operatorName 预约者的名字
     * @Description: 确定预约者是否已经存在
     * @return Boolean 存在返回true，不存在返回false
     */
    public Boolean checkOperatorExist(String operatorName){
        return operatorJPA.findByName(operatorName) != null;
    }

    /**
     * @param roomName 房间的名字
     * @Description: 确定房间是否已经存在
     * @return Boolean 存在返回true，不存在返回false
     */
    public Boolean checkRoomExist(String roomName){
        return roomJPA.findByName(roomName) != null;
    }

    /**
     * @param roomName 房间的名字
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @Description: 确定当前开始时间到结束时间是否冲突
     * @return Boolean 不冲突返回true，冲突返回false
     */
    public Boolean checkConflictsExist(String roomName, Date beginTime, Date endTime){
        if (checkRoomExist(roomName)){
    //        根据roomName查出room的Id
            Integer roomId = getRoomIdbyRoomName(roomName);
            List<Reserve> reserveList = reserveJPA.getReservesInConflict(roomId, beginTime, endTime);
            return reserveList.isEmpty();
        }
        return false;
    }

    public Integer getRoomIdbyRoomName(String roomName){
        return roomJPA.findByName(roomName).getRoomId();
    }

    public Integer getOperatorIdbyOperatorName(String operatorName){
        return operatorJPA.findByName(operatorName).getOperatorId();
    }
}
