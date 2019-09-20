package com.parkinfo.request.sysConfig;

import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuerySysRoleRequest extends PageRequest {


    @ApiModelProperty(value = "角色名称")
    private String name;

}
