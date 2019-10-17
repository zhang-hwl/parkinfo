package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ChangePassRequest {
    @ApiModelProperty(value = "员工id")
    @NotBlank(message = "员工id不能为空")
    private String id;

//    @ApiModelProperty(value = "旧密码")
//    @NotBlank(message = "旧密码不能为空")
//    private String oldPass;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6,message = "密码长度不能小于6位")
    private String newPass;
}
