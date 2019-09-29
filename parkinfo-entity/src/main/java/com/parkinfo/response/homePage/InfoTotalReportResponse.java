package com.parkinfo.response.homePage;

import com.parkinfo.enums.InfoReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class InfoTotalReportResponse {

    @ApiModelProperty(value = "基本信息类型，枚举", notes = "ENTER_TOP,HIGH_TAX,ADD_USER")
    private InfoReportType infoReportType;

    @ApiModelProperty("<K,V>")
    private List<BaseReportResponse> baseReportResponses;

}
