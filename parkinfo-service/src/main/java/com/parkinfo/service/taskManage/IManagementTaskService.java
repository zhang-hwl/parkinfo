package com.parkinfo.service.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddManagementTaskRequest;
import com.parkinfo.request.taskManage.QueryManagementTaskRequest;
import com.parkinfo.request.taskManage.SetTaskExecutedRequest;
import com.parkinfo.response.taskManage.ManagementTaskDetailResponse;
import com.parkinfo.response.taskManage.ManagementTaskListResponse;
import org.springframework.data.domain.Page;

/**
 * 管理制度
 */
public interface IManagementTaskService {

    /**
     * 查询管理制度任务
     * @param request
     * @return
     */
    Result<Page<ManagementTaskListResponse>> search(QueryManagementTaskRequest request);

    /**
     * 查询管理制度任务详情
     * @param id
     * @return
     */
    Result<ManagementTaskDetailResponse> detail(String id);

    /**
     * 创建管理制度任务
     * @param request
     * @return
     */
    Result addTask(AddManagementTaskRequest request);

    /**
     * 删除管理制度任务
     * @param taskId
     * @return
     */
    Result deleteTask(String taskId);

    /**
     * 设置管理制度任务任务完成情况
     * @param request
     * @return
     */
    Result setTaskExecuted(SetTaskExecutedRequest request);
}
