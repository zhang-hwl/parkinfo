package com.parkinfo.response.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParkInfoResponse {

    @ApiModelProperty("园区id")
    private String id;

    @ApiModelProperty("园区名称")
    private String name;
}
