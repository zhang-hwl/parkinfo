package com.parkinfo.response.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-28 14:09
 **/
@Data
public class RolePermissionListResponse {

    @ApiModelProperty(value = "父级权限")
    private List<ParkUserPermissionDTO> parentPermissionList;

    @ApiModelProperty(value = "子级权限")
    private List<ParkUserPermissionDTO> childPermissionList;
}
