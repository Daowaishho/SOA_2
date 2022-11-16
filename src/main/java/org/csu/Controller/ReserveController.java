package org.csu.Controller;

import org.csu.VO.Reserve;
import org.csu.common.CommonResponse;
import org.csu.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/Reserves")
public class ReserveController {
    @Autowired
    ReserveService reserveService;

    @PostMapping("/")
    public CommonResponse<Reserve> insertReserve(
            @RequestParam("operatorName") String operatorName,
            @RequestParam("roomName") String roomName,
            @RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime
            ){
        return reserveService.insertReserve(operatorName, roomName, beginTime, endTime);
    }

    @DeleteMapping("/{reserveId}")
    public CommonResponse<Reserve> deleteReserve(
            @PathVariable("reserveId") Integer reserveId){
        return reserveService.deleteReserve(reserveId);
    }

    @PutMapping("/{reserveId}")
    public CommonResponse<Reserve> updateReserve(
            @PathVariable("reserveId") Integer reserveId,
            @RequestParam("operatorName") String operatorName,
            @RequestParam("roomName") String roomName,
            @RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime
            ){
        return reserveService.updateReserve(reserveId, operatorName, roomName, beginTime, endTime);
    }

    @GetMapping("/")
    public CommonResponse<List<Reserve>> selectReserves(
            @RequestParam("operatorName") String operatorName,
            @RequestParam("roomName") String roomName,
            @RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime
            ){
        return reserveService.selectReserves(operatorName, roomName, beginTime, endTime);
    }
}
