package com.parkinfo.service.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddParkWorkPlanRequest;
import com.parkinfo.request.taskManage.ExportWorkPlanRequest;
import com.parkinfo.request.taskManage.QueryWorkPlanListRequest;
import com.parkinfo.request.taskManage.SetParkWorkPlanRequest;
import com.parkinfo.response.taskManage.ParkWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.ParkWorkPlanListResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;

/**
 * 园区工作计划及小节
 */
public interface IParkWorkPlanService {

    /**
     * 查询园区工作计划及小节
     * @param request
     * @return
     */
    Result<Page<ParkWorkPlanListResponse>> search(QueryWorkPlanListRequest request);

    /**
     * 查询园区工作计划及小节详情
     * @param id
     * @return
     */
    Result<ParkWorkPlanDetailResponse> detail(String id);

    /**
     * 创建园区工作计划及小节
     * @param request
     * @return
     */
    Result addTask(AddParkWorkPlanRequest request);

    /**
     * 编辑及提交任务
     * @param request
     * @return
     */
    Result setTask(SetParkWorkPlanRequest request);

    /**
     * 删除任务
     * @param id
     * @return
     */
    Result deleteTask(String id);

    /**
     * 导出园区工作计划及小节详情
     * @param request
     * @param response
     */
    void exportWorkPlan(ExportWorkPlanRequest request, HttpServletResponse response);
}
