package org.csu.persistence;


import org.csu.VO.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface ReserveJPA extends
//        SpringDataJPA提供的简单数据操作接口
        JpaRepository<Reserve, Integer>,
//SpringDataJPA提供的复杂查询接口
        JpaSpecificationExecutor<Reserve>,
//  序列化接口
        Serializable{
    @Query("SELECT r FROM Reserve r WHERE r.delFlag is true AND r.roomId = ?1 AND r.beginTime > ?2 AND r.beginTime < ?3 OR r.beginTime <= ?2 AND r.endTime >= ?3 OR r.endTime > ?2 AND r.endTime < ?3")
    List<Reserve> getReservesInConflict(Integer roomId, Date beginTime, Date endTime);
    Reserve findByReserveIdAndDelFlagEquals(Integer reserveId, Boolean delFlag);
    List<Reserve> findByBeginTimeAfterAndEndTimeBeforeAndDelFlagEquals(Date beginTime, Date endTime, Boolean delFlag);
    List<Reserve> findReservesByOperatorIdAndDelFlagEquals(Integer operatorId, Boolean delFlag);
    List<Reserve> findReservesByRoomIdAndDelFlagEquals(Integer roomId, Boolean delFlag);
}



