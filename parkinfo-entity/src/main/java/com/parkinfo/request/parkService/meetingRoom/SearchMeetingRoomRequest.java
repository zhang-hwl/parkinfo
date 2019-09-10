package com.parkinfo.request.parkService.meetingRoom;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchMeetingRoomRequest extends PageRequest {
    @ApiModelProperty("会议室id")
    private String meetingRoomId;
}
