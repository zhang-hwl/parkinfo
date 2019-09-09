package com.parkinfo.request.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SearchMeetingRoomReverseRequest {
    @ApiModelProperty("会议室id")
    @NotBlank(message = "会议室id不能为空")
    private String meetingRoomId;

    @ApiModelProperty(value = "查询时间",example = "1997-05-09")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @NotNull(message = "查询时间不能为空")
    private Date searchTime;
}
