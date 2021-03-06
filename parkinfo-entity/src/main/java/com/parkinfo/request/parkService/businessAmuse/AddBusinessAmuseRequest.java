package com.parkinfo.request.parkService.businessAmuse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddBusinessAmuseRequest {
    @ApiModelProperty("企业logo")
    private String logo;

    @ApiModelProperty("企业名称")
    @NotBlank(message = "名称不能为空")
    private String companyName;

    @ApiModelProperty("联系地址")
    @NotBlank(message = "联系地址不能为空")
    private String contactAddress;

    @ApiModelProperty("联系人")
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系人不能为空")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    @NotBlank(message = "联系人不能为空")
    private String businessHours;

    @ApiModelProperty("小类 id")
    @NotBlank(message = "类型不能为空")
    private String typeId;

    @ApiModelProperty("备注")
    private String remark;
}
