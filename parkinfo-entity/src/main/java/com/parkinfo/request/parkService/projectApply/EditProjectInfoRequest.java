package com.parkinfo.request.parkService.projectApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditProjectInfoRequest extends AddProjectInfoRequest{
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}
