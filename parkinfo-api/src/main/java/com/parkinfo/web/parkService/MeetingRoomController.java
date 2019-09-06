package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.SearchMeetingRoomRequest;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.parkService.IMeetingRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkService/meetingRoom")
@Api(value = "/parkService/meetingRoom", tags = {"园区服务-会议室预定"})
public class MeetingRoomController {

    @Autowired
    private IMeetingRoomService meetingRoomService;

    @PostMapping("/search")
    @ApiOperation(value = "分页获取本园区的会议室")
    private Result<Page<MeetingRoomResponse>> searchMeetingRoom(@RequestBody SearchMeetingRoomRequest request){
        return meetingRoomService.searchMeetingRoom(request);
    }
}
