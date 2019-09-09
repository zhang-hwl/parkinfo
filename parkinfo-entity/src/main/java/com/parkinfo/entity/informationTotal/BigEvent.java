package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

//园区大事件
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "c_big_event")
@ApiModel(value = "BigEvent", description = "信息统计-园区大事件")
public class BigEvent extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private String type;

    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;

    @Excel(name = "年份", width = 15)
    @ApiModelProperty(value = "年份")
    private String year;

    @Excel(name = "月份", width = 15)
    @ApiModelProperty(value = "月份")
    private String month;

    @Excel(name = "内容", width = 40)
    @ApiModelProperty(value = "内容")
    private String content;

    //todo 关联园区

}
