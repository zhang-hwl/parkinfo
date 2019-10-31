package com.parkinfo.request.infoTotalRequest;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
public class RoomInfoRequest{

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    @NotBlank(message = "版本标签不能为空")
    private String version;

    @ApiModelProperty(value = "房间号")
    @NotBlank(message = "房间号不能为空")
    private String roomId;

    @ApiModelProperty(value = "产权面积")
    private String area;

    @ApiModelProperty(value = "实际面积")
    private String actualArea;

    @ApiModelProperty(value = "租金")
    @NotBlank(message = "租金不能为空")
    private String rental;

    @ApiModelProperty(value = "物业费")
    private String property;

    @ApiModelProperty(value = "是否独立水表")
    @NotBlank(message = "请选择是否独立水表")
    private String waterMeter;

    @ApiModelProperty(value = "是否独立电表")
    @NotBlank(message = "请选择是否独立电表")
    private String ammeter;

    @ApiModelProperty(value = "是否有空调")
    @NotBlank(message = "请选择是否有空调")
    private String airConditioner;

    @ApiModelProperty(value = "装修程度")
    @NotBlank(message = "装修程度不能为空")
    private String decorate;

    @ApiModelProperty(value = "备注")
    private String remark;

//    @ApiModelProperty(value = "关联园区")
//    private ParkInfoResponse parkInfoResponse;

}
