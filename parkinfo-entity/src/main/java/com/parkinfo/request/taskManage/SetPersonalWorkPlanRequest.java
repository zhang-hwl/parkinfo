package com.parkinfo.request.taskManage;

import com.parkinfo.entity.taskManage.WorkPlanDetail;
import com.parkinfo.enums.PlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 15:50
 **/
@Data
public class SetPersonalWorkPlanRequest {

    @ApiModelProperty(value = "园区工作计划及小节id")
    private String id;

    @ApiModelProperty(value = "意见反馈")
    private String feedback;

    @ApiModelProperty(value = "是否同意")
    private Boolean agree;

    @ApiModelProperty(value = "任务名称")
    @Length(min = 0,max = 100,message = "任务名称不超过100个字")
    @NotNull(message = "任务名称不能为空")
    private String name;

    @ApiModelProperty(value = "任务类型")
    private PlanType planType;

    private List<WorkPlanDetail> workPlanDetailList;

    @ApiModelProperty(value = "结果反馈")
    private String resultFeedback;
}
