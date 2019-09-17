package com.parkinfo.response.companyManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EnterDetailResponse {
    @ApiModelProperty(value = "公司id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @ApiModelProperty(value = "公司成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date foundTime;

    @ApiModelProperty(value = "是否为百强企业")
    private String hundredCompany;

    @ApiModelProperty(value = "是否为本地企业")
    private String localCompany;

    @ApiModelProperty(value = "公司注册资金")
    private String registerMoney;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "入驻信息")
    private List<EnteredInfoResponse> infoResponseList;

    @ApiModelProperty(value = "附件信息")
    private List<EnclosureTotalResponse> enclosureTotalResponseList;
}
