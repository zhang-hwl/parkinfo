package com.parkinfo.entity.parkService.serviceFlow;


import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.ServiceFlowImgType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_service_flow_img")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ServiceFlowImg", description = "园区服务流程图")
public class ServiceFlowImg extends BaseEntity {

    @ApiModelProperty("流程图类型")
    @Enumerated(EnumType.ORDINAL)
    private ServiceFlowImgType imgType;

    @ApiModelProperty("图片地址")
    @Column(columnDefinition = "text")
    private String path;

    @ManyToOne()
    @JoinColumn(name = "park_id")
    private ParkInfo parkInfo;
}
