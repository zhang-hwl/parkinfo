package com.parkinfo.response.homePage;

import com.parkinfo.enums.InfoReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganInfoReportResponse {

    @ApiModelProperty(value = "基本信息类型")
    private String infoReportName;

    @ApiModelProperty("值")
    private String value;

}
