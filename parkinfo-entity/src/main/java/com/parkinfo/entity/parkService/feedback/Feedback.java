package com.parkinfo.entity.parkService.feedback;

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
@Table(name = "c_feedback")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "Feedback", description = "意见反馈")
public class Feedback extends BaseEntity {
    @ApiModelProperty("运营方名称")
    private String operatorName;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;

    @ApiModelProperty("问题描述")
    private String questionDescription;

    @ApiModelProperty("图片地址")
    private String imgPath;
}
