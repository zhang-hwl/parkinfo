package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//竞争园区信息
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_comptete_graden_info")
@ApiModel(value = "CompeteGradenInfo", description = "信息统计-竞争园区统计信息")
public class CompeteGradenInfo extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;

}
