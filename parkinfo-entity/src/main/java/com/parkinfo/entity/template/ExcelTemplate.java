package com.parkinfo.entity.template;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.enums.TemplateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "c_excel_template")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "ExcelTemplate", description = "园区服务流程图")
public class ExcelTemplate extends BaseEntity {

    @ApiModelProperty("模板类型")
    @Enumerated(EnumType.ORDINAL)
    private TemplateType type;

    @ApiModelProperty("模板地址")
    private String url;

    @ApiModelProperty("模板名称")
    private String excelName;
}
