package com.parkinfo.response.taskManage;

import com.parkinfo.entity.taskManage.WorkPlanDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 15:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkWorkPlanDetailResponse extends ParkWorkPlanListResponse {

    @ApiModelProperty(value = "反馈")
    private String feedback;

    private List<WorkPlanDetail> workPlanDetailList;
}
