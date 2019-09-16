package com.parkinfo.request.taskManage;

import com.parkinfo.enums.TaskType;
import com.parkinfo.request.base.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 11:11
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryManagementTaskRequest extends PageRequest {

    @ApiModelProperty(value = "开始时间起")
    private Date startTimeFrom;

    @ApiModelProperty(value = "开始时间止")
    private Date startTimeTo;

    @ApiModelProperty(value = "结束时间起")
    private Date endTimeFrom;

    @ApiModelProperty(value = "结束时间止")
    private Date endTimeTo;

    @ApiModelProperty(value = "园区id")
    private String parkId;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "发起或接受")
    @NotNull(message = "类型必传")
    private TaskType taskType;
}
