package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.parkinfo.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

//本园区房间统计
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "c_room_info")
@ApiModel(value = "RoomInfo", description = "本园区房间统计")
public class RoomInfo extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @Excel(name = "房间号", width = 15)
    @ApiModelProperty(value = "房间号")
    private String roomId;

    @Excel(name = "产权面积(平方米)", width = 15)
    @ApiModelProperty(value = "产权面积")
    private String area;

    @Excel(name = "实际面积(平方米)", width = 15)
    @ApiModelProperty(value = "实际面积")
    private String actualArea;

    @Excel(name = "租金(元/每平方米每月)", width = 15)
    @ApiModelProperty(value = "租金")
    private String rental;

    @Excel(name = "物业费(元/每平方米每月)", width = 15)
    @ApiModelProperty(value = "物业费")
    private String property;

    @Excel(name = "是否独立水表", width = 15)
    @ApiModelProperty(value = "是否独立水表")
    private String waterMeter;

    @Excel(name = "是否有空调", width = 15)
    @ApiModelProperty(value = "是否有空调")
    private String airConditioner;

    @Excel(name = "装修程度", width = 15)
    @ApiModelProperty(value = "装修程度")
    private String decorate;

    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;

    //关联园区 todo

}
