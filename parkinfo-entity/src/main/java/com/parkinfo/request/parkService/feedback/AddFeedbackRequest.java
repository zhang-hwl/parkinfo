package com.parkinfo.request.parkService.feedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddFeedbackRequest {
    @ApiModelProperty("运营方名称")
    @NotBlank(message = "运营方名称不能为空")
    private String operatorName;

    @ApiModelProperty("问题描述")
    @NotBlank(message = "问题描述不能为空")
    private String questionDescription;

    @ApiModelProperty("图片地址")
    private String imgPath;
}
