package com.parkinfo.request.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

//    @ApiModelProperty("园区id")
//    @NotBlank(message = "园区id不能为空")
//    private String id;

    @ApiModelProperty("账户")
    @NotBlank(message = "账户不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
