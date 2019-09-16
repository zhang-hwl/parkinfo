package com.parkinfo.entity.companyManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_entered_info")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "EnteredInfo", description = "企业入驻信息")
public class EnteredInfo extends BaseEntity {

    @ApiModelProperty(value = "入驻房间号")
    private String roomNum;

    @ApiModelProperty(value = "入驻面积")
    private String enterArea;

    @ApiModelProperty(value = "人员规模")
    private Integer peopleNum;

    @ApiModelProperty(value = "第几次续约")
    private String continueNext;

    @ApiModelProperty(value = "续约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date continueTime;

    @ApiModelProperty(value = "上半年税收")
    private BigDecimal halfTax;

    @ApiModelProperty(value = "上半年产值")
    private BigDecimal halfProduct;

    @ApiModelProperty(value = "本年预计税收")
    private BigDecimal predictTax;

    @ApiModelProperty(value = "本年第一季度税收")
    private BigDecimal firstQuarter;

    @ApiModelProperty(value = "本年第二季度税收")
    private BigDecimal secondQuarter;

    @ApiModelProperty(value = "本年第三季度税收")
    private BigDecimal thirdQuarter;

    @ApiModelProperty(value = "本年第四季度税收")
    private BigDecimal fourthQuarter;

    @ManyToOne
    @JoinColumn(name = "company_detail_id")
    @ApiModelProperty(value = "企业申请入驻详情")
    @JsonIgnore
    private CompanyDetail companyDetail;
}
