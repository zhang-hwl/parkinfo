package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class InfoEquipmentRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "编号")
    private String serialNumber;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "基本参数")
    private String basicParam;

    @ApiModelProperty(value = "数量")
    private String number;

    @ApiModelProperty(value = "购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date buyTime;

    @ApiModelProperty(value = "目前状态")
    private String status;

    @ApiModelProperty(value = "保管人")
    private String preserver;

    @ApiModelProperty(value = "负责人")
    private String head;

    @ApiModelProperty(value = "照片")
    private String img;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "关联园区")
    private ParkInfoResponse parkInfoResponse;

}
