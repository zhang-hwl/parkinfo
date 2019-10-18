package com.parkinfo.entity.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.CheckStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//需求
@EqualsAndHashCode(callSuper = true, exclude = {"companyType","parkInfo"})
@Data
@Entity
@Table(name = "c_company_demand")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "CompanyDemand", description = "需求详情")
public class CompanyDemand extends BaseEntity {

    @Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @Excel(name = "主要业务", width = 15)
    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @Excel(name = "职位", width = 15)
    @ApiModelProperty(value = "职位")
    private String position;

    @Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Excel(name = "需求类型", width = 15)
    @ApiModelProperty(value = "需求类型")
    private String requireType;

    @Excel(name = "需求详情", width = 30)
    @ApiModelProperty(value = "需求详情")
    private String requireDetail;

    @Excel(name = "需求时间", width = 15)
    @ApiModelProperty(value = "需求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date requireTime;

    @Excel(name = "需求位置", width = 15)
    @ApiModelProperty(value = "需求位置")
    private String requireSite;

    @Excel(name = "对接人", width = 15)
    @ApiModelProperty(value = "对接人")
    private String connectMan;

    @ApiModelProperty(value = "需求面积")
    private String requireArea;



    @ApiModelProperty(value = "审核状态")
    @Enumerated(EnumType.ORDINAL)//APPLYING,AGREE,REFUSE 申请中,同意,拒绝
    private CheckStatus checkStatus;

    @ManyToOne()
    @JoinColumn(name = "park_id")
    @JsonIgnore
    private ParkInfo parkInfo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private CompanyType companyType;

}
