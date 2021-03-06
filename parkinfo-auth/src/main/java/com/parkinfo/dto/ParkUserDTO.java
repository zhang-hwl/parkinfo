package com.parkinfo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 09:44
 **/
@Data
public class ParkUserDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "账户昵称")
    private String nickname;

    @ApiModelProperty(value = "学位证书")
    private String diploma;

    @ApiModelProperty(value = "资格证书")
    private String credentials;

    @ApiModelProperty(value = "紧急联系人")
    private String urgencyContent;

    @ApiModelProperty(value = "紧急联系电话")
    private String urgencyPhone;

    @ApiModelProperty(value = "权限列表")
    private List<ParkUserPermissionDTO> permissions;

    @ApiModelProperty(value = "角色")
    private List<String> role;

    @ApiModelProperty(value = "当前登录园区Id")
    private String currentParkId;
}
