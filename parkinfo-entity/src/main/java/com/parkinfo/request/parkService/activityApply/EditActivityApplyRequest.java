package com.parkinfo.request.parkService.activityApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class EditActivityApplyRequest extends AddActivityApplyRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}
