package com.parkinfo.request.parkService.activityApply;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchActivityApplyRequest extends PageRequest {
    @ApiModelProperty("活动名称")
    private String activityName;
}
