package com.parkinfo.response.taskManage;

import com.parkinfo.entity.taskManage.WorkPlanDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 15:49
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonalWorkPlanDetailResponse extends PersonalWorkPlanListResponse {

    private List<WorkPlanDetail> workPlanDetails;
}
