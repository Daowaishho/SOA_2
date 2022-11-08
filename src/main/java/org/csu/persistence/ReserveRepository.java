package org.csu.persistence;


import org.csu.VO.Reserve;
import org.springframework.data.repository.Repository;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Repository
public interface ReserveRepository extends Repository<Reserve, Integer> {
    List<Reserve> getReservesByOperatorNameAndRoomIdAndBeginTimeAndEndTime(String operatorName,
                                                                           Integer roomId,
                                                                           Date beginTime,
                                                                           Date endTime);
}
