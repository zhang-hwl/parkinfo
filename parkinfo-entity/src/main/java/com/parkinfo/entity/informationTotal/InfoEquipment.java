package com.parkinfo.entity.informationTotal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//信息化设备
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"parkInfo"})
@Entity
@Table(name = "c_info_equipment")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
@ApiModel(value = "InfoEquipment", description = "信息统计-信息化设备")
public class InfoEquipment extends BaseEntity {

    @ApiModelProperty(value = "版本标签")
    private String version;

    @Excel(name = "编号", width = 15, fixedIndex = 0)
    @ApiModelProperty(value = "编号")
    private String serialNumber;

    @Excel(name = "设备名称", width = 15, fixedIndex = 1)
    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @Excel(name = "基本参数", width = 15, fixedIndex = 2)
    @ApiModelProperty(value = "基本参数")
    private String basicParam;

    @Excel(name = "数量(台)", width = 15, fixedIndex = 3)
    @ApiModelProperty(value = "数量")
    private String number;

    @Excel(name = "购买时间(年-月-日)",width = 15, databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd", fixedIndex = 4)
    @ApiModelProperty(value = "购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buyTime;

    @Excel(name = "目前状态", width = 15, fixedIndex = 5)
    @ApiModelProperty(value = "目前状态")
    private String status;

    @Excel(name = "保管人", width = 15, fixedIndex = 6)
    @ApiModelProperty(value = "保管人")
    private String preserver;

    @Excel(name = "负责人", width = 15, fixedIndex = 7)
    @ApiModelProperty(value = "负责人")
    private String head;

    @Excel(name = "照片", width = 15, fixedIndex = 8)
    @ApiModelProperty(value = "照片")
    private String img;

    @Excel(name = "备注", width = 30, fixedIndex = 9)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parkInfo_id")
    //关联园区信息
    private ParkInfo parkInfo;


}
