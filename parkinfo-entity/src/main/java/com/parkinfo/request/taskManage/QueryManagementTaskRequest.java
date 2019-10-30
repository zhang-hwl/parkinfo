package com.parkinfo.request.taskManage;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "提交时间起")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTimeFrom;

    @ApiModelProperty(value = "提交时间止")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTimeTo;


    @ApiModelProperty(value = "园区id")
    private String parkId;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "发起或接受")
    @NotNull(message = "类型必传")
    private TaskType taskType;
}
