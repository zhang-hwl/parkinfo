package com.parkinfo.request.compayManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SetCompanyInfoRequest {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "联系人")
    @NotBlank(message = "联系人不能为空")
    private String linkMan;

    @ApiModelProperty(value = "联系电话")
    @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}$",message = "手机号格式不正确")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;
}
