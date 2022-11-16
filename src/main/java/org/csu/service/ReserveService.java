package org.csu.service;

import org.csu.VO.Reserve;
import org.csu.common.CommonResponse;

import java.util.Date;
import java.util.List;

public interface ReserveService {
    CommonResponse<Reserve> insertReserve(String operatorName, String roomName, Date beginTime, Date endTime);
    CommonResponse<Reserve> deleteReserve(Integer reserveId);
    CommonResponse<Reserve> updateReserve(Integer reserveId,String operatorName,String roomName,Date beginTime,Date endTime);
    CommonResponse<List<Reserve>> selectReserves(String operatorName,String roomName,Date beginTime,Date endTime);
}
