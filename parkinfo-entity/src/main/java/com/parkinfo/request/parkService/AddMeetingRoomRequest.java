package com.parkinfo.request.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class AddMeetingRoomRequest {

    @ApiModelProperty(value = "会议室名称")
    private String roomName;

    @ApiModelProperty(value = "容纳人数")
    private Integer capacity;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "备注")
    private String remark;

}
