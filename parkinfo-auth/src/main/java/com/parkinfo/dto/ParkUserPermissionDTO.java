package com.parkinfo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-05 10:15
 **/
@Data
public class ParkUserPermissionDTO {

    @ApiModelProperty(value = "权限id")
    private String id;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限描述
     */
    @ApiModelProperty(value = "权限描述")
    private String remark;

    @ApiModelProperty(value = "路由")
    private String path;

    @ApiModelProperty(value = "上级权限id")
    private String parentId;

    @ApiModelProperty(value = "icon")
    private String src;

    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private String priority;
}
