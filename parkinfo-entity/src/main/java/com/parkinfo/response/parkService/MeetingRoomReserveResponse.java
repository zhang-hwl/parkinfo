package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MeetingRoomReserveResponse {
    @ApiModelProperty(value = "预定开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "预定结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty("预订人id")
    private String userId;

    @ApiModelProperty("会议室预约时间区间")
    private String meetingRoomSections;
}
