package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SetCompanyRequireRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "需求类型")
    private String requireType;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "需求时间")
    private String requireTime;

    @ApiModelProperty(value = "需求位置")
    private String requireSite;

    @ApiModelProperty(value = "对接人")
    private String connectMan;

    @ApiModelProperty(value = "需求详情")
    private String requireDetail;
}
