package com.parkinfo.response.parkService;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class CompanyDataResponse {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @ApiModelProperty(value = "联系人")
    @NotBlank(message = "联系人不能为空")
    private String linkMan;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "主要业务")
    @NotBlank(message = "主要业务不能为空")
    private String mainBusiness;

    @ApiModelProperty(value = "需求面积")
    @NotBlank(message = "需求面积不能为空")
    private String requireArea;

    @ApiModelProperty(value = "公司注册资金")
    @NotBlank(message = "公司注册资金不能为空")
    private String registerMoney;

    @ApiModelProperty(value = "是否为百强企业")
    @NotBlank(message = "是否为百强企业不能为空")
    private String hundredCompany;

    @ApiModelProperty(value = "是否为本地企业")
    @NotBlank(message = "是否为本地企业不能为空")
    private String localCompany;

    @ApiModelProperty(value = "公司成立日期")
    @NotNull(message = "公司成立日期不能为空")
    private Date foundTime;

    @ApiModelProperty(value = "入驻状态,WAITING,ENTERED,LEAVE,未入驻,已入住,已离园")
    @NotNull(message = "入驻状态不能为空")
    private EnterStatus enterStatus;

    @ApiModelProperty(value = "补充资料")
    private Set<EnclosureTotal> enclosureTotals = new HashSet<>();
}
