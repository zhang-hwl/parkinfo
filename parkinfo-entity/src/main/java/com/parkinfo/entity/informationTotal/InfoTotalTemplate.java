package com.parkinfo.entity.informationTotal;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_info_template")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "InfoTotalTemplate", description = "信息统计-文件模板")
public class InfoTotalTemplate extends BaseEntity {

    private String type;

    private String templateUrl;

}
