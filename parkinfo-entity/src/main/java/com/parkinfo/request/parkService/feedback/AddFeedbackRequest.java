package com.parkinfo.request.parkService.feedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddFeedbackRequest {
    @ApiModelProperty("运营方名称")
    private String operatorName;

    @ApiModelProperty("问题描述")
    private String questionDescription;

    @ApiModelProperty("图片地址")
    private String imgPath;
}
