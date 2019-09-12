package com.parkinfo.entity.parkService.projectApply;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_project_type")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ProjectType", description = "项目类别")
public class ProjectType extends BaseEntity {

    @ApiModelProperty("名称")
    private String value;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;
}
