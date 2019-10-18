package com.parkinfo.response.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ManagementResponse {
    @ApiModelProperty(value = "公司id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    @Excel(name = "公司名称", width = 15)
    private String companyName;

    @ApiModelProperty(value = "公司注册资金")
    @Excel(name = "公司注册资金", width = 15)
    private String registerMoney;

    @ApiModelProperty(value = "是否为百强企业")
    @Excel(name = "百强企业", width = 15)
    private String hundredCompany;

    @ApiModelProperty(value = "是否为本地企业")
    @Excel(name = "本地企业", width = 15)
    private String localCompany;

    @ApiModelProperty(value = "联系人")
    @Excel(name = "联系人", width = 15)
    private String linkMan;

    @ApiModelProperty(value = "职位")
    @Excel(name = "职位", width = 15)
    private String position;

    @ApiModelProperty(value = "联系电话")
    @Excel(name = "联系电话", width = 15)
    private String phone;

    @ApiModelProperty(value = "主要业务")
    @Excel(name = "主要业务", width = 15)
    private String mainBusiness;

    @ApiModelProperty(value = "需求面积")
    @Excel(name = "需求面积", width = 15)
    private String requireArea;

    @ApiModelProperty(value = "公司成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "公司成立日期", width = 15)
    private Date foundTime;

    @ApiModelProperty(value = "对接时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "对接时间", width = 15)
    private Date connectTime;

    @ApiModelProperty(value = "对接方式")
    @Excel(name = "对接时间", width = 15)
    private String connectWay;

    @ApiModelProperty(value = "入驻状态")
    //WAITING,ENTERED,LEAVE 未入驻,已入住,已离园
    private EnterStatus enterStatus;

    @ApiModelProperty(value = "是否有意向")
    @Excel(name = "是否有意向", width = 15)
    private String purpose;

    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "管理公司信息人")
    @Excel(name = "管理公司信息人", width = 15)
    private String nickname;

    @ApiModelProperty(value = "管理公司信息人id")
    private String manId;

}
