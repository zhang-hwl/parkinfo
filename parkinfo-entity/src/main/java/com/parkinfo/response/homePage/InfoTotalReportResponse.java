package com.parkinfo.response.homePage;

import com.parkinfo.enums.InfoReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class InfoTotalReportResponse {

    @ApiModelProperty(value = "基本信息类型")
    private String infoReportName;

    @ApiModelProperty("<K,V>,K代表园区名称，V为值")
    private List<BaseReportResponse> baseReportResponses;

}
