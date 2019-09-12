package com.parkinfo.response.parkService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectInfoResponse {
    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目奖励")
    private String projectPraise;

    @ApiModelProperty("项目要求")
    private String projectRequire;

    @ApiModelProperty("分类名称")
    private String typeName;
}
