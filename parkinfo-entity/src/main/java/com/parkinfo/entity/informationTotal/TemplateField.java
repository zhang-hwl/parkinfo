package com.parkinfo.entity.informationTotal;

import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_info_template_field")
@ApiModel(value = "模板中某些字段的内容")
public class TemplateField extends BaseEntity {

    @ApiModelProperty(value = "大类")
    private String general;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "项目")
    private String project;



}
