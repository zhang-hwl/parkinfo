package com.parkinfo.request.template;

import com.parkinfo.enums.TemplateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "ExcelTemplateRequest", description = "模板上传")
public class ExcelTemplateRequest {

    @ApiModelProperty("类型id")
    private String typeId;

    @ApiModelProperty("模板地址")
    private String url;

}
