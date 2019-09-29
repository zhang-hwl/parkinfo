package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

//本园区房间统计
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo"})
@Entity
@Table(name = "c_room_info")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@ApiModel(value = "RoomInfo", description = "本园区房间统计")
public class RoomInfo extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @Excel(name = "房间号", width = 15, fixedIndex = 0)
    @ApiModelProperty(value = "房间号")
    private String roomId;

    @Excel(name = "产权面积(平方米)", width = 15, fixedIndex = 1)
    @ApiModelProperty(value = "产权面积")
    private String area;

    @Excel(name = "实际面积(平方米)", width = 15, fixedIndex = 2)
    @ApiModelProperty(value = "实际面积")
    private String actualArea;

    @Excel(name = "租金(元/每平方米每月)", width = 15, fixedIndex = 3)
    @ApiModelProperty(value = "租金")
    private String rental;

    @Excel(name = "物业费(元/每平方米每月)", width = 15, fixedIndex = 4)
    @ApiModelProperty(value = "物业费")
    private String property;

    @Excel(name = "是否独立水表", width = 15, fixedIndex = 5)
    @ApiModelProperty(value = "是否独立水表")
    private String waterMeter;

    @Excel(name = "是否独立电表", width = 15, fixedIndex = 6)
    @ApiModelProperty(value = "是否独立电表")
    private String ammeter;

    @Excel(name = "是否有空调", width = 15, fixedIndex = 7)
    @ApiModelProperty(value = "是否有空调")
    private String airConditioner;

    @Excel(name = "装修程度", width = 15, fixedIndex = 8)
    @ApiModelProperty(value = "装修程度")
    private String decorate;

    @Excel(name = "备注", width = 15, fixedIndex = 9)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;

}
