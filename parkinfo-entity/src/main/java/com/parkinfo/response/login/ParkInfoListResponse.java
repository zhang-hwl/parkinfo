package com.parkinfo.response.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-19 14:33
 **/
@Data
public class ParkInfoListResponse {

    @ApiModelProperty(value = "园区id")
    private String id;

    @ApiModelProperty(value = "园区名称")
    private String name;
}
