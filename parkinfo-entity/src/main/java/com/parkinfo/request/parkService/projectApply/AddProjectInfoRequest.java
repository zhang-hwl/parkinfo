package com.parkinfo.request.parkService.projectApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddProjectInfoRequest {
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目奖励")
    private String projectPraise;

    @ApiModelProperty("项目要求")
    private String projectRequire;

    @ApiModelProperty("分类id")
    private String typeId;
}
