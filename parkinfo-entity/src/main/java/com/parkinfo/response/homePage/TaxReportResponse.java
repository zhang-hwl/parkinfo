package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaxReportResponse extends BaseReportResponse{
    
    @ApiModelProperty("累积园区税收")
    private String revenueCount;

}
