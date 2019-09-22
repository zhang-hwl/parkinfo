package com.parkinfo.entity.parkService.projectApply;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "c_project_info")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ProjectInfo", description = "项目信息")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class ProjectInfo extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProjectType projectType;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目奖励")
    private String projectPraise;

    @ApiModelProperty("项目要求")
    private String projectRequire;
}
