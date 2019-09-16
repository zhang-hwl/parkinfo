package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddUserRequest {

    @ApiModelProperty(value = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像url")
    private String avatar;

    @ApiModelProperty(value = "绑定角色id")
    private List<String> roleId;
}
