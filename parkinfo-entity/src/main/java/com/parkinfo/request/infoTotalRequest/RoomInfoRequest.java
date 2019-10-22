package com.parkinfo.request.infoTotalRequest;

import com.parkinfo.entity.base.BaseEntity;
import com.parkinfo.response.login.ParkInfoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class RoomInfoRequest{

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本标签")
    private String version;

    @ApiModelProperty(value = "房间号")
    private String roomId;

    @ApiModelProperty(value = "产权面积")
    private String area;

    @ApiModelProperty(value = "实际面积")
    private String actualArea;

    @ApiModelProperty(value = "租金")
    private String rental;

    @ApiModelProperty(value = "物业费")
    private String property;

    @ApiModelProperty(value = "是否独立水表")
    private String waterMeter;

    @ApiModelProperty(value = "是否独立电表")
    private String ammeter;

    @ApiModelProperty(value = "是否有空调")
    private String airConditioner;

    @ApiModelProperty(value = "装修程度")
    private String decorate;

    @ApiModelProperty(value = "备注")
    private String remark;

//    @ApiModelProperty(value = "关联园区")
//    private ParkInfoResponse parkInfoResponse;

}
