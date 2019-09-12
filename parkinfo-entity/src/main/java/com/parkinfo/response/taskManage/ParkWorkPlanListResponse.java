package com.parkinfo.response.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinfo.entity.taskManage.WorkPlanDetail;
import com.parkinfo.enums.PlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 14:41
 **/
@Data
public class ParkWorkPlanListResponse {

    @ApiModelProperty(value = "任务id")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private PlanType planType;

    @ApiModelProperty(value = "园区名称")
    private String parkName;

    @ApiModelProperty(value = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "提交人")
    private String author;

    @ApiModelProperty(value = "进度")
    private Integer step;

    @ApiModelProperty(value = "是否完成")
    private Boolean finished;

}
