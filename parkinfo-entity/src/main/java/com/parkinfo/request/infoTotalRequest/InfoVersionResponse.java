package com.parkinfo.request.infoTotalRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InfoVersionResponse {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本名称")
    @NotBlank(message = "版本名称不能为空")
    private String version;

    @ApiModelProperty(value = "大类名称")
    @NotBlank(message = "所属分类不能为空")
    private String general;

}
