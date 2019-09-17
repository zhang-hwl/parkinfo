package com.parkinfo.response.companyManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.CheckStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class CompanyResponse {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "需求类型")
    private String requireType;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "需求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date requireTime;

    @ApiModelProperty(value = "需求地点")
    private String requireSite;

    @ApiModelProperty(value = "对接人")
    private String connectMan;

    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

    @ApiModelProperty(value = "审核状态")
    @Enumerated(EnumType.ORDINAL)
    private CheckStatus checkStatus;
}
