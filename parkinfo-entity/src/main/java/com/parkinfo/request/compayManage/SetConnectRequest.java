package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SetConnectRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "对接时间")
    private Date connectTime;

    @ApiModelProperty(value = "是否有意向")
    private String purpose;

    @ApiModelProperty(value = "对接备注")
    private String remark;
}
