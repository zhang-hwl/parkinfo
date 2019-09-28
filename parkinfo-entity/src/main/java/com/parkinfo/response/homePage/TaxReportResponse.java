package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class TaxReportResponse{
    
    @ApiModelProperty("累积园区税收")
    private String revenueCount;

    @ApiModelProperty("<K,V>")
    private List<BaseReportResponse> baseReportResponses;

}
