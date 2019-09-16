package com.parkinfo.service.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddGovernmentReportRequest;
import com.parkinfo.request.taskManage.QueryGovernmentReportRequest;
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
}
