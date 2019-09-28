package com.parkinfo.response.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRoleResponse {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String remark;


    @ApiModelProperty("id")
    private String id;

}
