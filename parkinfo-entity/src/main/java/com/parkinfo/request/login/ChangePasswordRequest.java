package com.parkinfo.request.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-17 13:34
 **/
@Data
public class ChangePasswordRequest {

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "密码不能为空")
    @Size(min=6,message = "密码长度至少为6位")
    private String password;
}
