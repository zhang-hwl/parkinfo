package com.parkinfo.service.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddGovernmentReportRequest;
import com.parkinfo.request.taskManage.QueryGovernmentReportRequest;
import com.parkinfo.request.taskManage.SetTaskExecutedRequest;
import com.parkinfo.response.taskManage.GovernmentReportDetailResponse;
import com.parkinfo.response.taskManage.GovernmentReportListResponse;
import org.springframework.data.domain.Page;

public interface IGovernmentReportService {

    /**
     * 查询政府工作汇报任务
     * @param request
     * @return
     */
    Result<Page<GovernmentReportListResponse>> search(QueryGovernmentReportRequest request);

    /**
     * 查询政府工作汇报任务详情
     * @param id
     * @return
     */
    Result<GovernmentReportDetailResponse> detail(String id);

    /**
     * 创建政府工作汇报任务
     * @param request
     * @return
     */
    Result addTask(AddGovernmentReportRequest request);

    /**
     * 删除政府工作汇报任务
     * @param taskId
     * @return
     */
    Result deleteTask(String taskId);

    /**
     * 设置政府工作汇报任务完成情况
     * @param request
     * @return
     */
    Result setTaskExecuted(SetTaskExecutedRequest request);
}
