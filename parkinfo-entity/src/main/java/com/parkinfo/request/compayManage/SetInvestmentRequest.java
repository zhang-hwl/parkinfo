package com.parkinfo.request.compayManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.DiscussStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class SetInvestmentRequest {
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
    @NotBlank(message = "主要业务不能为空")
    private String mainBusiness;

    @ApiModelProperty(value = "公司成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @NotNull(message = "公司成立时间不能为空")
    private Date foundTime;

    @ApiModelProperty(value = "是否为百强企业")
    @NotBlank(message = "是否为百强企业不能为空")
    private String hundredCompany;

    @ApiModelProperty(value = "是否为本地企业")
    @NotBlank(message = "是否为本地企业不能为空")
    private String localCompany;

    @ApiModelProperty(value = "公司注册资金")
    @NotBlank(message = "注册资本不能为空")
    private String registerMoney;

    @ApiModelProperty(value = "需求面积")
    @NotBlank(message = "需求面积不能为空")
    private String requireArea;

    @ApiModelProperty(value = "对接时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @NotNull(message = "对接时间不能为空")
    private Date connectTime;

    @ApiModelProperty(value = "对接方式")
    @NotBlank(message = "对接方式不能为空")
    private String connectWay;

    @ApiModelProperty(value = "是否有意向")
    @NotBlank(message = "意向状态不能为空")
    private String purpose;

    @ApiModelProperty(value = "对接备注")
    private String remark;

    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    @NotNull(message = "洽谈状态不能为空")
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "洽谈内容")
    private String content;

    @ApiModelProperty(value = "洽谈内容备注")
    private String remarkTalk;

    @ApiModelProperty(value = "类型id")
    private String typeId;
}
