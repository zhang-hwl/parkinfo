package com.parkinfo.entity.companyManage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.enums.EnclosureType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_enclosure_total")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "EnclosureTotal", description = "所有附件")
public class EnclosureTotal extends BaseEntity {

    @ApiModelProperty(value = "附件类型")
    @Enumerated(EnumType.ORDINAL)
    private EnclosureType enclosureType;

    @ApiModelProperty(value = "附件url")
    private String fileUrl;

    @ManyToOne
    @ApiModelProperty(value = "企业申请入驻详情")
    @JsonIgnore
    private CompanyDetail companyDetail;
}
