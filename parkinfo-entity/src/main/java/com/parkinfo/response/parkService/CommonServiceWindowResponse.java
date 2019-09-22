package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class CommonServiceWindowResponse {
    @ApiModelProperty("公共服务窗口  小类")
    private CommonServiceWindowType type;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("服务名称")
    private String serviceName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("联系地址")
    private String contactAddress;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("营业时间")
    private String businessHours;

    @ApiModelProperty("业务详情")
    private String businessDetails;

    @ApiModelProperty("id")
    private String id;
}
