package org.csu.service.impl;

import org.csu.VO.Operator;
import org.csu.VO.Reserve;
import org.csu.VO.Room;
import org.csu.persistence.OperatorJPA;
import org.csu.persistence.ReserveJPA;
import org.csu.persistence.RoomJPA;
import org.csu.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                checkConflictsExist(roomName, beginTime, endTime) &&
                beginTime.before(endTime)){
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

    /**
     * @param reserveId 要删除的预约记录的Id
     * @Description: 根据id删除预约记录
     */
    @Override
    public void deleteReserve(Integer reserveId) {
        Reserve reserve = reserveJPA.findByReserveId(reserveId);
        reserveJPA.delete(reserve);
    }

    /**
     * @param reserveId 预约记录的ID
     * @param operatorName 已存在的预约者的名字
     * @param roomName 已存在的房间的名字
     * @param beginTime 开始的时间
     * @param endTime 结束的时间
     * @Description: 预约者预约房间的一段时间，由开始时间和结束时间确定
     * @return Reserve
     */
    @Override
    public Reserve updateReserve(Integer reserveId, String operatorName, String roomName, Date beginTime, Date endTime) {
//        若该记录存在
        Reserve oldReserve = reserveJPA.findByReserveId(reserveId);
        if (oldReserve != null){
//        因为要先除开要修改的记录,先把该记录删了
          reserveJPA.delete(oldReserve);
//        用户和房间都存在且该房间beginTime到endTime之间的时间段是否没有冲突
        if (checkOperatorExist(operatorName) &&
            checkRoomExist(roomName) &&
            checkConflictsExist(roomName, beginTime, endTime) &&
            beginTime.before(endTime)){
//          更新预约信息
                oldReserve.setOperatorId(getOperatorIdbyOperatorName(operatorName));
                oldReserve.setRoomId(getRoomIdbyRoomName(roomName));
                oldReserve.setBeginTime(beginTime);
                oldReserve.setEndTime(endTime);
            }
            return reserveJPA.save(oldReserve);
        }
        return null;
    }

    /**
     * @param operatorName 预约者名字的模糊查询关键字
     * @param roomName 房间名字的模糊查询关键字
     * @param beginTime 开始的时间
     * @param endTime 结束的时间
     * @Description: 按照预约者名字和房间名进行模糊查询，查询出所有包括在开始时间到结束时间之间的预约记录
     * @return Reserve
     */
    @Override
    public List<Reserve> selectReserves(String operatorName, String roomName, Date beginTime, Date endTime) {
        List<Reserve> satisfactoryReserveList = null;
//        对用户进行查询
        List<Operator> operatorList = operatorJPA.findByNameLike("%"+operatorName+"%");
//    对房间进行查询
        List<Room> roomList = roomJPA.findRoomsByNameLike("%"+roomName+"%");
//      对在(beginTime,endTime)之间时间段的所有预约进行查询
        List<Reserve> reserveList = reserveJPA.findByBeginTimeAfterAndEndTimeBefore(beginTime, endTime);
//        三者都不为空
        if (operatorList != null && roomList!= null && reserveList != null){
//        遍历reserveList
            satisfactoryReserveList = new ArrayList<Reserve>();
            for (Reserve reserve : reserveList) {
                Operator operator = operatorJPA.findByOperatorId(reserve.getOperatorId());
                Room room = roomJPA.findByRoomId(reserve.getRoomId());
                if (operatorList.contains(operator) && roomList.contains(room)){
                    satisfactoryReserveList.add(reserve);
                }
            }
        }
        return satisfactoryReserveList;
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
