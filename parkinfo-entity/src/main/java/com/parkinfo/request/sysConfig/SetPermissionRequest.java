package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SetPermissionRequest {

    @ApiModelProperty(value = "权限id")
    private List<String> permissionIds;

    @ApiModelProperty(value = "角色id")
    private String roleId;
}
