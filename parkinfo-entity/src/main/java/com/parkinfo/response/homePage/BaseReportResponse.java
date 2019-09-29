package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseReportResponse {

    @ApiModelProperty("横坐标Key")
    private String key;

    @ApiModelProperty("纵坐标Value")
    private String value;


}
