package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddUserRequest {

    @ApiModelProperty(value = "登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String account;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "头像url")
    private String avatar;

    @ApiModelProperty(value = "绑定园区id")
    private String parkId;

    @ApiModelProperty(value = "绑定园区id")
    private List<String> parkIds;

    @ApiModelProperty(value = "绑定角色id")
    @NotBlank(message = "角色不能为空")
    private String roleId;

    //身份证
    @ApiModelProperty("身份证")
    @NotBlank(message = "身份证不能为空")
    private String idCard;

    //学位证书
    @ApiModelProperty("学位证书")
    private String diploma;

    //资格证书
    @ApiModelProperty("资格证书")
    private String credentials;

    //紧急联系人
    @ApiModelProperty("紧急联系人")
    @NotBlank(message = "紧急联系人不能为空")
    private String urgencyContent;

    //紧急联系电话
    @ApiModelProperty("紧急联系电话")
    @NotBlank(message = "紧急联系电话不能为空")
    private String urgencyPhone;

    @ApiModelProperty("企业id，角色为HR时必传")
    private String companyId;
}
