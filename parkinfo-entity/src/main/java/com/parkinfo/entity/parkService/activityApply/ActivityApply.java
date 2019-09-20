package com.parkinfo.entity.parkService.activityApply;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
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
@Table(name = "c_activity_apply")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ActivityApply",description =  "协助举办活动")
public class ActivityApply extends BaseEntity {
    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date activityTime;

    @ApiModelProperty("活动描述")
    private String activityDescription;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyDetail companyDetail;
}
