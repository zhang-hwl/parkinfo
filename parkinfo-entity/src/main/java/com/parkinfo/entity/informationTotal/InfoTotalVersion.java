package com.parkinfo.entity.informationTotal;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_info_version")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "InfoTotalVersion", description = "信息统计-文件版本")
public class InfoTotalVersion extends BaseEntity {

    @ApiModelProperty(value = "版本名称")
    private String version;

    @ApiModelProperty(value = "大类名称")
    private String general;

}
