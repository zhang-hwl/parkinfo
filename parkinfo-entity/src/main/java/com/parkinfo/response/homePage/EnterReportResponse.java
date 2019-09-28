package com.parkinfo.response.homePage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class EnterReportResponse{

    @ApiModelProperty("园区名称")
    private String parkName;

//    @ApiModelProperty("园区入驻企业数量")
//    private String parkEnterCount;

    @ApiModelProperty("累计已入驻企业数")
    private String enterCount;

    @ApiModelProperty("<K,V>")
    private List<BaseReportResponse> baseReportResponses;

}
