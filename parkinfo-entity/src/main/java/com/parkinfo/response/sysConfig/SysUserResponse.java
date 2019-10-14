package com.parkinfo.response.sysConfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.response.login.ParkInfoListResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysUserResponse {

    @ApiModelProperty("id")
    private String id;

    /**
     * 账户
     */
    @ApiModelProperty("账户")
    private String account;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    //身份证
    @ApiModelProperty("身份证")
    private String idCard;

    //学位证书
    @ApiModelProperty("学位证书")
    private String diploma;

    //资格证书
    @ApiModelProperty("资格证书")
    private String credentials;

    //紧急联系人
    @ApiModelProperty("紧急联系人")
    private String urgencyContent;

    //紧急联系电话
    @ApiModelProperty("紧急联系电话")
    private String urgencyPhone;

    @ApiModelProperty("角色列表")
    private List<SysRoleResponse> roleResponses;

    @ApiModelProperty("园区列表")
    private List<ParkInfoListResponse> parkInfoListResponses;

    @ApiModelProperty("企业id")
    private String companyId;


}
