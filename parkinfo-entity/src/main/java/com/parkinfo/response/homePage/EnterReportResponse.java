package com.parkinfo.response.homePage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnterReportResponse extends BaseReportResponse{

    @ApiModelProperty("园区名称")
    private String parkName;

    @ApiModelProperty("累计已入驻企业数")
    private String enterCount;

}
