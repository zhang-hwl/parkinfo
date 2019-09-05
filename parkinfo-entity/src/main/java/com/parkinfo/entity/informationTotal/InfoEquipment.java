package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

//信息化设备
@Data
@Entity
@Table(name = "c_info-equipment")
@ApiModel(value = "InfoEquipment", description = "信息统计-信息化设备")
public class InfoEquipment extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "编号", width = 15)
    @ApiModelProperty(value = "编号")
    private String serialNumber;

    @Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @Excel(name = "基本参数", width = 15)
    @ApiModelProperty(value = "基本参数")
    private String basicParam;

    @Excel(name = "数量(台)", width = 15)
    @ApiModelProperty(value = "数量")
    private String number;

    @Excel(name = "购买时间(年-月-日)", format = "yyyy-MM-dd", width = 15)
    @ApiModelProperty(value = "购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String buyTime;

    @Excel(name = "目前状态", width = 15)
    @ApiModelProperty(value = "目前状态")
    private String status;

    @Excel(name = "保管人", width = 15)
    @ApiModelProperty(value = "保管人")
    private String preserver;

    @Excel(name = "负责人", width = 15)
    @ApiModelProperty(value = "负责人")
    private String head;

    @Excel(name = "照片", width = 15)
    @ApiModelProperty(value = "照片")
    private String img;

    @Excel(name = "备注", width = 30)
    @ApiModelProperty(value = "备注")
    private String remark;

    //关联园区， todo

}
