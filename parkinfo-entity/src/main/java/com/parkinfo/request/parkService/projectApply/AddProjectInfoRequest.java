package com.parkinfo.request.parkService.projectApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddProjectInfoRequest {

    @ApiModelProperty("项目名称")
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @ApiModelProperty("项目奖励")
    private String projectPraise;

    @ApiModelProperty("项目要求")
    private String projectRequire;

    @ApiModelProperty("分类id")
    @NotBlank(message = "项目类型不能为空")
    private String typeId;
}
