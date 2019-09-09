package com.parkinfo.entity.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.enums.DiscussStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_discuss_detail")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "DiscussDetail", description = "洽谈详情")
public class DiscussDetail extends BaseEntity {

    @Excel(name = "对接方式", width = 15)
    @ApiModelProperty(value = "对接方式")
    private String connectWay;

    @Excel(name = "洽谈状态", width = 15)
    @ApiModelProperty(value = "洽谈状态")//WAIT_LOOK,LOOKED,FOLLOWING,FIRST_PASS 未参园,已参观,跟进中,第一次通过
    private DiscussStatus discussStatus;

    @ApiModelProperty(value = "洽谈内容")
    @Column(columnDefinition = "text")
    private String content;

    @ApiModelProperty(value = "备注")
    @Column(columnDefinition = "text")
    private String remark;

    @OneToOne
    @JsonIgnore
    @ApiModelProperty(value = "企业申请入驻详情")
    private CompanyDetail companyDetail;
}
