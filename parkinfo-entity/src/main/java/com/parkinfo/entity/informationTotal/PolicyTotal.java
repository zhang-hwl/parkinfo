package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

//政策统计
@Data
@Entity
@Table(name = "c_policy_total")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PolicyTotal", description = "信息统计-政策统计")
public class PolicyTotal extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;

    @Excel(name = "项目", width = 15)
    @ApiModelProperty(value = "项目")
    private String project;

    @Excel(name = "地方留存比例(%)", width = 15)
    @ApiModelProperty(value = "地方留存比例")
    //可以为字符串，例如有，无，待补充
    private String remainRatio;

    @Excel(name = "入驻第一年(%)", width = 15)
    @ApiModelProperty(value = "入驻第一年")
    private String enterOne;

    @Excel(name = "入驻第二年(%)", width = 15)
    @ApiModelProperty(value = "入驻第二年")
    private String enterTwo;

    @Excel(name = "入驻第三年(%)", width = 15)
    @ApiModelProperty(value = "入驻第三年")
    private String enterThree;

    @Excel(name = "入驻第四年(%)", width = 15)
    @ApiModelProperty(value = "入驻第四年")
    private String enterFour;

    @Excel(name = "入驻第五年(%)", width = 15)
    @ApiModelProperty(value = "入驻第五年")
    private String enterFive;

    @Excel(name = "说明", width = 30)
    @ApiModelProperty(value = "说明")
    private String remark;

    //关联园区， todo
}