package com.parkinfo.service.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.*;
import com.parkinfo.response.taskManage.ParkWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.ParkWorkPlanListResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanListResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 13:37
 **/
public interface IPersonalWorkPlanService {
    /**
     * 查询个人工作计划及小节
     * @param request
     * @return
     */
    Result<Page<PersonalWorkPlanListResponse>> search(QueryPersonalPlanListRequest request);

    /**
     * 查询个人工作计划及小节详情
     * @param id
     * @return
     */
    Result<PersonalWorkPlanDetailResponse> detail(String id);

    /**
     * 创建个人工作计划及小节
     * @param request
     * @return
     */
    Result addTask(AddPersonalWorkPlanRequest request);

    /**
     * 编辑及提交任务
     * @param request
     * @return
     */
    Result setTask(SetPersonalWorkPlanRequest request);

    /**
     * 删除任务
     * @param id
     * @return
     */
    Result deleteTask(String id);

    /**
     *导出个人工作计划及小节详情
     * @param request
     */
    void exportWorkPlan(ExportWorkPlanRequest request, HttpServletResponse response);
}
