package com.parkinfo.request.taskManage;

import com.parkinfo.enums.PlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 15:49
 **/
@Data
public class AddPersonalWorkPlanRequest {

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "任务类型")
    private PlanType planType;

    @ApiModelProperty(value = "工作详情")
    private List<WorkPlanDetailRequest> workPlanDetailRequestList;
}
