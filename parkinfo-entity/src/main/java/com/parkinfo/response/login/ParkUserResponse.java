package com.parkinfo.response.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ParkUserResponse {

    @ApiModelProperty("用户")
    private String id;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;
}
