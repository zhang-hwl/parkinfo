package com.parkinfo.response.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysParkInfoResponse {

    @ApiModelProperty("园区id")
    private String id;

    @ApiModelProperty("园区名称")
    private String name;

    @ApiModelProperty("园区管理人id")
    private String userId;

    @ApiModelProperty("园区负责人名称")
    private String userName;

    @ApiModelProperty(value = "是否可用")
    private Boolean available;

    @ApiModelProperty(value = "开户账号")
    private String openId;

    @ApiModelProperty(value = "开户行")
    private String openBank;

    @ApiModelProperty(value = "营业执照")
    private String permit;

    @ApiModelProperty(value = "开户许可证")
    private String license;

}
