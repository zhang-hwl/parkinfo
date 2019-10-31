package com.parkinfo.request.parkService.meetingRoom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class AddMeetingRoomRequest {

    @ApiModelProperty(value = "会议室名称")
    @NotBlank(message = "会议室名称不能为空")
    private String roomName;

    @ApiModelProperty(value = "容纳人数")
    @NotBlank(message = "容纳人数不能为空")
    private Integer capacity;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "备注")
    private String remark;

}
