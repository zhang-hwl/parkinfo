package com.parkinfo.response.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-19 14:38
 **/
@Data
public class LoginResponse {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "园区列表")
    private List<ParkInfoListResponse> parkList;
}
