package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BindCompanyRequest {

    @ApiModelProperty(value = "公司id")
    private String companyId;

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
