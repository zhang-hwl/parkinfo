package com.parkinfo.request.parkService.activityApply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AddActivityApplyRequest {

    @ApiModelProperty("活动名称")
    @NotBlank(message = "活动名称不能为空")
    private String activityName;

    @ApiModelProperty("活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @NotNull(message = "活动时间不能为空")
    private Date activityTime;

    @ApiModelProperty("活动描述")
    @NotBlank(message = "活动描述不能为空")
    private String activityDescription;

    @ApiModelProperty("联系人")
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String contactNumber;
}

