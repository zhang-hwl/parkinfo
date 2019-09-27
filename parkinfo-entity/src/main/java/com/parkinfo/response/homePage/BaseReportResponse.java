package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseReportResponse {

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("value")
    private String value;


}
