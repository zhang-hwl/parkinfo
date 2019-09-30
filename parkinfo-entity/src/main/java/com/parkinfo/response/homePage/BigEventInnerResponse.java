package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BigEventInnerResponse {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("月")
    private String month;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("园区名称")
    private String parkName;

}
