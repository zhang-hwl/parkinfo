package com.parkinfo.entity.parkService.projectApply;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.enums.ProjectApplyStatus;
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
@Table(name = "c_project_apply_record")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ProjectApplyRecord", description = "项目申报记录")
public class ProjectApplyRecord extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "company_id")
    @ApiModelProperty("关联企业")
    private CompanyDetail companyDetail;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @ApiModelProperty("关联项目")
    private ProjectInfo projectInfo;

    @ApiModelProperty("申报日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date appleDate;

    @ApiModelProperty("项目申请状态")
    @Enumerated(EnumType.ORDINAL)
    private ProjectApplyStatus status;
}
