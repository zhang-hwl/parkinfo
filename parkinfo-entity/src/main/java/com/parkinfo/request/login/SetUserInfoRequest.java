package com.parkinfo.request.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-17 13:44
 **/
@Data
public class SetUserInfoRequest {

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "头像")
    @NotBlank(message = "头像不能为空")
    private String avatar;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "学位证书")
    private String diploma;

    @ApiModelProperty(value = "资格证书")
    private String credentials;

    @ApiModelProperty(value = "紧急联系人")
    @NotBlank(message = "紧急联系人不能为空")
    private String urgencyContent;

    @ApiModelProperty(value = "紧急联系电话")
    @NotBlank(message = "紧急联系电话不能为空")
    private String urgencyPhone;
}
