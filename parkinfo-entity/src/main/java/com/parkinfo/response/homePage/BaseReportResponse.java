package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseReportResponse {

    @ApiModelProperty("横坐标")
    private String key;

    @ApiModelProperty("纵坐标")
    private String value;


}
