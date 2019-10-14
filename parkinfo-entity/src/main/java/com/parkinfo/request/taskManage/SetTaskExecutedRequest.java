package com.parkinfo.request.taskManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-10-14 15:10
 **/
@Data
public class SetTaskExecutedRequest {

    @ApiModelProperty(value = "任务id")
    @NotBlank(message = "任务id不能为空")
    private String taskId;

    @ApiModelProperty(value = "是否执行")
    @NotNull(message = "执行状态不能为空")
    private Boolean executed;
}
