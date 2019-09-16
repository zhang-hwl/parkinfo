package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddSysRoleRequest {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色备注")
    private String remark;
}
