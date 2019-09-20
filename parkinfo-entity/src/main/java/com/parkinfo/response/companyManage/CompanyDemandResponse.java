package com.parkinfo.response.companyManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CompanyDemandResponse {

    @ApiModelProperty(value = "公司id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "需求类型")
    private String requireType;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "需求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date requireTime;

    @ApiModelProperty(value = "需求位置")
    private String requireSite;

    @ApiModelProperty(value = "对接人")
    private String connectMan;

    @ApiModelProperty(value = "需求详情")
    private String requireDetail;
}
