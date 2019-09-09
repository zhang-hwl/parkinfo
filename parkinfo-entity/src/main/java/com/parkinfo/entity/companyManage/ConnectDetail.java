package com.parkinfo.entity.companyManage;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
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
@Table(name = "c_connect_detail")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ConnectDetail", description = "对接详情")
public class ConnectDetail extends BaseEntity {

    @Excel(name = "对接时间", width = 15)
    @ApiModelProperty(value = "对接时间")
    private Date connectTime;

    @Excel(name = "意向", width = 15)
    @ApiModelProperty(value = "是否有意向")
    private String purpose;

    @ApiModelProperty(value = "备注")
    @Column(columnDefinition = "text")
    private String remark;

    @OneToOne
    @JsonIgnore
    @ApiModelProperty(value = "企业申请入驻详情")
    private CompanyDetail companyDetail;
}
