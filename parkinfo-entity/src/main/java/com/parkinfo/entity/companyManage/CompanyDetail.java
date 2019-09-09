package com.parkinfo.entity.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.CheckStatus;
import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_company_detail")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "CompanyDetail", description = "企业申请入驻详情")
public class CompanyDetail extends BaseEntity{

    @Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "公司注册资金")
    private String registerMoney;

    @Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Excel(name = "职位", width = 15)
    @ApiModelProperty(value = "职位")
    private String position;

    @Excel(name = "主要业务", width = 15)
    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @Excel(name = "需求类型", width = 15)
    @ApiModelProperty(value = "需求类型")
    private String requireType;

    @Excel(name = "需求面积", width = 15)
    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @Excel(name = "需求时间", width = 15)
    @ApiModelProperty(value = "需求时间")
    private String requireTime;

    @Excel(name = "需求位置", width = 15)
    @ApiModelProperty(value = "需求位置")
    private String requireSite;

    @Excel(name = "对接人", width = 15)
    @ApiModelProperty(value = "对接人")
    private String connectMan;

    @Excel(name = "需求详情", width = 30)
    @ApiModelProperty(value = "需求详情")
    @Column(columnDefinition = "text")
    private String requireDetail;

    @Excel(name = "续约次数", width = 15)
    @ApiModelProperty(value = "续约次数")
    private Integer number;

    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

    @ApiModelProperty(value = "公司成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date foundTime;

    @ApiModelProperty(value = "审核状态")
    @Enumerated(EnumType.ORDINAL)//APPLYING,AGREE,REFUSE 申请中,同意,拒绝
    private CheckStatus checkStatus;

    @ApiModelProperty(value = "入驻状态")
    @Enumerated(EnumType.ORDINAL)//WAITING,ENTERED,LEAVE  未入住,已入住,已离园
    private EnterStatus enterStatus;

    @Excel(name = "百强企业", width = 15)
    @ApiModelProperty(value = "是否为百强企业")
    private String hundredCompany;

    @Excel(name = "本地企业", width = 15)
    @ApiModelProperty(value = "是否为本地企业")
    private String localCompany;

    @ManyToOne()
    @JoinColumn(name = "park_id")
    @JsonIgnore
    private ParkInfo parkInfo;

    @OneToOne(mappedBy = "companyDetail",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_detail_id")
    @JsonIgnore
    @ApiModelProperty(value = "对接详情")
    private ConnectDetail connectDetail;

    @OneToOne(mappedBy = "companyDetail",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_detail_id")
    @JsonIgnore
    @ApiModelProperty(value = "洽谈详情")
    private DiscussDetail discussDetail;
}
