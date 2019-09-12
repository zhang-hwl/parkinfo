package com.parkinfo.request.parkService.projectApply;

import com.parkinfo.enums.ProjectApplyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ChangeStatusRequest {
    @ApiModelProperty("recordId")
    @NotBlank(message = "recordId不能为空")
    private String recordId;

    @ApiModelProperty(value = "状态",allowableValues = "APPLYING,FINISHED,REFUSED")
    @NotNull(message = "状态不能为空")
    private ProjectApplyStatus status;
}
