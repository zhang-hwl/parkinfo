package com.parkinfo.request.infoTotalRequest;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InfoEquipmentRequest {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    @NotBlank(message = "版本标签不能为空")
    private String version;

    @ApiModelProperty(value = "编号")
    private String serialNumber;

    @ApiModelProperty(value = "设备名称")
    @NotBlank(message = "设备名称不能为空")
    private String equipmentName;

    @ApiModelProperty(value = "基本参数")
    private String basicParam;

    @ApiModelProperty(value = "数量")
    @NotBlank(message = "数量不能为空")
    private String number;

    @ApiModelProperty(value = "购买时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "购买时间不能为空")
    private Date buyTime;

    @ApiModelProperty(value = "目前状态")
    @NotBlank(message = "目前状态不能为空")
    private String status;

    @ApiModelProperty(value = "保管人")
    private String preserver;

    @ApiModelProperty(value = "负责人")
    private String head;

    @ApiModelProperty(value = "照片")
    @NotBlank(message = "照片不能为空")
    private String img;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "关联园区")
    @NotNull(message = "所属园区不能为空")
    private ParkInfoResponse parkInfoResponse;

}
