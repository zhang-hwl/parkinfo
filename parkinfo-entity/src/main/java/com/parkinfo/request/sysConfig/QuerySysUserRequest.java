package com.parkinfo.request.sysConfig;


import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySysUserRequest extends PageRequest {

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    @ApiModelProperty(value = "是否可用")
    private Boolean available;
}
