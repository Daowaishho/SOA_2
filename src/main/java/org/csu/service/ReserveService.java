package org.csu.service;

import org.csu.VO.Reserve;

import java.util.Date;
import java.util.List;

public interface ReserveService {
    Reserve insertReserve(String operatorName, String roomName, Date beginTime, Date endTime);
    void deleteReserve(Integer reserveId);
    Reserve updateReserve(Integer reserveId,String operatorName,String roomName,Date beginTime,Date endTime);
    List<Reserve> selectReserves(String operatorName,String roomName,Date beginTime,Date endTime);
}
