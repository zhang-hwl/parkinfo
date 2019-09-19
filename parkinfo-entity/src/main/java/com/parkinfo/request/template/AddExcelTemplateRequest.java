package com.parkinfo.request.template;

import com.parkinfo.enums.TemplateType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class AddExcelTemplateRequest {

    @ApiModelProperty("模板类型")
    private TemplateType type;

    @ApiModelProperty("模板地址")
    private String url;

    @ApiModelProperty("模板名称")
    private String excelName;
}
