package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BindCompanyRequest {

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
