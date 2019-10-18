package com.parkinfo.request.taskManage;

import com.parkinfo.entity.taskManage.WorkPlanDetail;
import com.parkinfo.enums.PlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 14:50
 **/
@Data
public class SetParkWorkPlanRequest {

    @ApiModelProperty(value = "园区工作计划及小节id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "意见反馈")
    private String feedback;

    @ApiModelProperty(value = "是否同意")
    @NotNull(message = "是否同意不能为空")
    private Boolean agree;

    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String name;

    @ApiModelProperty(value = "任务类型")
    @NotNull(message = "任务类型不能为空")
    private PlanType planType;

    private List<WorkPlanDetail> workPlanDetailList;

    @ApiModelProperty(value = "结果反馈")
    private String resultFeedback;
}
