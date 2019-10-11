package com.parkinfo.request.parkService.activityApply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AddActivityApplyRequest {

    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date activityTime;

    @ApiModelProperty("活动描述")
    private String activityDescription;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;
}

