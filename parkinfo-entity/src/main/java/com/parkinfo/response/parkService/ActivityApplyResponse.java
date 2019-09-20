package com.parkinfo.response.parkService;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.userConfig.ParkInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class ActivityApplyResponse {
    @ApiModelProperty("活动名称")
    private String activityName;

    @ApiModelProperty("活动时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date activityTime;

    @ApiModelProperty("活动描述")
    private String activityDescription;

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系方式")
    private String contactNumber;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("公司名称")
    private String companyName ;
}
