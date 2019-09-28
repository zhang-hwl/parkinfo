package com.parkinfo.request.parkService.commonServiceWindow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class AddCommonServiceWindowRequest {
    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;

    @ApiModelProperty("业务详情")
    private String businessDetails;

    @ApiModelProperty("小类 id")
    private String typeId;

    @ApiModelProperty("备注")
    private String remark;
}
