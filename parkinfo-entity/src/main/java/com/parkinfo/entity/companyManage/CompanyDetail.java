package com.parkinfo.entity.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.CheckStatus;
import com.parkinfo.enums.DiscussStatus;
import com.parkinfo.enums.EnterStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

//招商信息表
@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo", "parkUser", "enteredInfos", "enclosureTotals", "companyType"})
@Data
@Entity
@Table(name = "c_company_detail")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "CompanyDetail", description = "企业申请入驻详情")
public class CompanyDetail extends BaseEntity{

    @Excel(name = "对接时间", width = 15, fixedIndex = 1)
    @ApiModelProperty(value = "对接时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date connectTime;

    @Excel(name = "公司名称", width = 15, fixedIndex = 2)
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @Excel(name = "百强企业", width = 15, fixedIndex = 3)
    @ApiModelProperty(value = "是否为百强企业")
    private String hundredCompany;

    @Excel(name = "本地企业", width = 15, fixedIndex = 4)
    @ApiModelProperty(value = "是否为本地企业")
    private String localCompany;

    @Excel(name = "意向", width = 15, fixedIndex = 5)
    @ApiModelProperty(value = "是否有意向")
    private String purpose;

    @ApiModelProperty(value = "公司注册资金")
    @Excel(name = "公司注册资金", width = 15, fixedIndex = 6)
    private String registerMoney;

    @ApiModelProperty(value = "公司成立日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Excel(name = "公司注册资金", width = 15, fixedIndex = 7)
    private Date foundTime;

    @Excel(name = "主要业务", width = 15, fixedIndex = 8)
    @ApiModelProperty(value = "主要业务")
    private String mainBusiness;

    @Excel(name = "联系人", width = 15, fixedIndex = 9)
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @Excel(name = "职位", width = 10)
    @ApiModelProperty(value = "职位")
    private String position;

    @Excel(name = "联系电话", width = 15, fixedIndex = 11)
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Excel(name = "对接方式", width = 15, fixedIndex = 12)
    @ApiModelProperty(value = "对接方式")
    private String connectWay;

    @ApiModelProperty(value = "洽谈情况")
    @Column(columnDefinition = "text")
    @Excel(name = "洽谈情况", width = 15, fixedIndex = 13)
    private String content;

    @Excel(name = "需求面积", width = 15, fixedIndex = 14)
    @ApiModelProperty(value = "需求面积")
    private String requireArea;

    @ApiModelProperty(value = "对接备注")
    @Column(columnDefinition = "text")
    @Excel(name = "备注", width = 15, fixedIndex = 15)
    private String remark;

//    @Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

//    @Excel(name = "需求类型", width = 15)
    @ApiModelProperty(value = "需求类型")
    private String requireType;

//    @Excel(name = "需求时间", width = 15)
    @ApiModelProperty(value = "需求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date requireTime;

//    @Excel(name = "需求位置", width = 15)
    @ApiModelProperty(value = "需求位置")
    private String requireSite;

//    @Excel(name = "对接人", width = 15)
    @ApiModelProperty(value = "对接人")
    private String connectMan;

//    @Excel(name = "需求详情", width = 30)
    @ApiModelProperty(value = "需求详情")
    @Column(columnDefinition = "text")
    private String requireDetail;

//    @Excel(name = "续约次数", width = 15)
    @ApiModelProperty(value = "续约次数")
    private Integer number;

    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyTime;

    @ApiModelProperty(value = "入驻状态")
    @Enumerated(EnumType.ORDINAL)//WAITING,ENTERED,LEAVE 未入驻,已入住,已离园
    private EnterStatus enterStatus;

//    @Excel(name = "洽谈状态", width = 15)
    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "洽谈内容备注")
    @Column(columnDefinition = "text")
    private String remarkTalk;

    @Column(name = "`deleteEnter`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否删除入驻企业")
    private Boolean deleteEnter;

    @Column(name = "`entered`", columnDefinition = "tinyint(1)")
    @ApiModelProperty(value = "是否入驻")
    private Boolean entered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_id")
    @JsonIgnore
    private ParkInfo parkInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private ParkUser parkUser;

    @OneToMany(mappedBy = "companyDetail",fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(value = "入驻信息")
    private Set<EnteredInfo> enteredInfos = new HashSet<>();

    @OneToMany(mappedBy = "companyDetail",fetch = FetchType.LAZY)
    @JsonIgnore
    @ApiModelProperty(value = "企业附件")
    private Set<EnclosureTotal> enclosureTotals = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private CompanyType companyType;
}
