package org.csu.Controller;

import org.csu.VO.Room;
import org.csu.common.CommonResponse;
import org.csu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Rooms")
public class RoomController {
    @Autowired
    RoomService roomService;

    @PostMapping("/")
    public CommonResponse<Room> insertRoom(
            @RequestParam("roomName") String roomName,
            @RequestParam("available") Boolean available){
        return roomService.insertRoom(roomName, available);
    }

    @DeleteMapping("/{roomName}")
    public CommonResponse<Room> deleteRoom(
            @PathVariable("roomName") String roomName){
        return roomService.deleteRoom(roomName);
    };

    @PutMapping("/{roomName}")
    public CommonResponse<Room> updateRoom(
            @PathVariable("roomName") String roomName,
            @RequestParam("available") Boolean available){
        return roomService.updateRoom(roomName, available);
    }

    @GetMapping("/{roomName}")
    public CommonResponse<List<Room>> selectRooms(
            @PathVariable("roomName") String roomName
    ){
        return roomService.selectRooms(roomName);
    }
}
