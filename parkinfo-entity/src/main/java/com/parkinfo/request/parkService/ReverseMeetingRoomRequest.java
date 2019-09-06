package com.parkinfo.request.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class ReverseMeetingRoomRequest {
    @ApiModelProperty("会议室id")
    @NotBlank(message = "会议室id不能为空")
    private String meetingRoomId;

    @ApiModelProperty("开始时间")
    @NotEmpty(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty("结束时间")
    @NotEmpty(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
}
