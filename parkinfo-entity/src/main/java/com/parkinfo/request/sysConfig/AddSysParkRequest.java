package com.parkinfo.request.sysConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddSysParkRequest {

    @ApiModelProperty("园区名称")
    @NotBlank(message = "园区名称不能为空")
    private String parkName;

    @ApiModelProperty("园区负责人id")
    private String userId;

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
