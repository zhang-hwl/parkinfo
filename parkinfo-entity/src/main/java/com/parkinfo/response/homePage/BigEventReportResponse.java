package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BigEventReportResponse {

    @ApiModelProperty("年")
    private String year;

    List<BigEventInnerResponse> bigEventInnerResponses;

}
