package com.parkinfo.response.homePage;

import com.parkinfo.enums.InfoReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganInfoReportResponse {

    @ApiModelProperty(value = "基本信息类型,横坐标，枚举", notes = "ENTER_TOP,HIGH_TAX,ADD_USER")
    private InfoReportType infoReportType;

    @ApiModelProperty("值")
    private String value;

}
