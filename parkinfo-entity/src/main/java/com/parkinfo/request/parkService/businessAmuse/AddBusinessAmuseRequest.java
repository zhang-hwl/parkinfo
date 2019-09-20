package com.parkinfo.request.parkService.businessAmuse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddBusinessAmuseRequest {
    @ApiModelProperty("企业logo")
    private String logo;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;

    @ApiModelProperty("小类 id")
    private String typeId;

    @ApiModelProperty("备注")
    private String remark;
}
