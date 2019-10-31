package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BindCompanyRequest {

    @ApiModelProperty(value = "公司id")
    @NotBlank(message = "公司不能为空")
    private String companyId;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户不能为空")
    private String userId;

}
