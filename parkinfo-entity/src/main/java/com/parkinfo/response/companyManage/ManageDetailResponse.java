package com.parkinfo.response.companyManage;

import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ManageDetailResponse {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司注册资金")
    private String registerMoney;

    @ApiModelProperty(value = "是否为百强企业")
    private String hundredCompany;

    @ApiModelProperty(value = "是否为本地企业")
    private String localCompany;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "对接时间")
    private Date connectTime;

    @ApiModelProperty(value = "是否有意向")
    private String purpose;

    @ApiModelProperty(value = "对接备注")
    private String remark;

    @ApiModelProperty(value = "对接方式")
    private String connectWay;

    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "洽谈内容")
    private String content;

    @ApiModelProperty(value = "洽谈内容备注")
    private String remarkTalk;

    @ApiModelProperty(value = "入驻状态")
    private EnterStatus enterStatus;
}
