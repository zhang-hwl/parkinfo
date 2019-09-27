package com.parkinfo.response.homePage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BigEventResponse {

    @ApiModelProperty("年")
    private String year;

    @ApiModelProperty("月")
    private String month;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("园区名称")
    private String parkName;
}
