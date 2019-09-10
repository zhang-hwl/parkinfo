package com.parkinfo.request.parkService.meetingRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditMeetingRoomRequest extends AddMeetingRoomRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "会议室id不能为空")
    private String roomId;
}
