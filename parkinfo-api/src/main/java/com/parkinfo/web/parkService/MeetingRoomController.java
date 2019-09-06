package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.*;
import com.parkinfo.response.parkService.MeetingRoomReserveResponse;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.parkService.IMeetingRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parkService/meetingRoom")
@Api(value = "/parkService/meetingRoom", tags = {"园区服务-会议室预定"})
public class MeetingRoomController {

    private final IMeetingRoomService meetingRoomService;

    public MeetingRoomController(IMeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    @PostMapping("/search")
    @ApiOperation(value = "分页获取本园区的会议室")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoom:search")
    private Result<Page<MeetingRoomResponse>> searchMeetingRoom(@Valid @RequestBody SearchMeetingRoomRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<MeetingRoomResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return meetingRoomService.searchMeetingRoom(request);
    }

    @PostMapping("/reserve/search")
    @ApiOperation(value = "查询会议室当天的预定记录")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoom:reserveSearch")
    private Result<List<MeetingRoomReserveResponse>> searchMeetingRoomResrve(@Valid @RequestBody SearchMeetingRoomReverseRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<List<MeetingRoomReserveResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return meetingRoomService.searchMeetingRoomReserve(request);
    }

    @PostMapping("/reverse")
    @ApiOperation(value = "预约会议室")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoom:reverse")
    private Result<String> reverseMeetingRoom(@Valid @RequestBody ReverseMeetingRoomRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return meetingRoomService.reverseMeetingRoom(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "创建园区会议室")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoomManage:add")
    private Result<String> addMeetingRoom(@Valid @RequestBody AddMeetingRoomRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return meetingRoomService.addMeetingRoom(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "修改园区会议室")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoomManage:edit")
    private Result<String> editMeetingRoom(@Valid @RequestBody EditMeetingRoomRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return meetingRoomService.editMeetingRoom(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除园区会议室")
    @RequiresPermissions("parkService:meetingRoomReserve:meetingRoomManage:delete")
    private Result<String> deleteMeetingRoom(@PathVariable("id") String id){
        return meetingRoomService.deleteMeetingRoom(id);
    }
}