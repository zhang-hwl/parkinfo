package com.parkinfo.request.parkService.commonServiceWindow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class AddCommonServiceWindowRequest {
    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("服务名称")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    @ApiModelProperty("联系地址")
    @NotBlank(message = "联系地址不能为空")
    private String contactAddress;

    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    @NotBlank(message = "营业时间不能为空")
    private String businessHours;

    @ApiModelProperty("业务详情")
    @NotBlank(message = "业务详情不能为空")
    private String businessDetails;

    @ApiModelProperty("小类 id")
    @NotBlank(message = "分类不能为空")
    private String typeId;

    @ApiModelProperty("备注")
    private String remark;
}
