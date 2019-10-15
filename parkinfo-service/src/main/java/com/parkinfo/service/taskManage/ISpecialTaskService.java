package com.parkinfo.service.taskManage;


import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddPersonalWorkPlanRequest;
import com.parkinfo.request.taskManage.AddSpecialTaskRequest;
import com.parkinfo.request.taskManage.QuerySpecialTaskRequest;
import com.parkinfo.request.taskManage.SetTaskExecutedRequest;
import com.parkinfo.response.taskManage.SpecialTaskDetailResponse;
import com.parkinfo.response.taskManage.SpecialTaskListResponse;
import org.springframework.data.domain.Page;

public interface ISpecialTaskService {

    /**
     * 查询专项任务
     * @param request
     * @return
     */
    Result<Page<SpecialTaskListResponse>> search(QuerySpecialTaskRequest request);

    /**
     * 查询专项任务详情
     * @param id
     * @return
     */
    Result<SpecialTaskDetailResponse> detail(String id);

    /**
     * 创建专项任务
     * @param request
     * @return
     */
    Result addTask(AddSpecialTaskRequest request);

    /**
     * 删除专项任务
     * @param taskId
     * @return
     */
    Result deleteTask(String taskId);

    /**
     * 设置专项任务完成情况
     * @param request
     * @return
     */
    Result setTaskExecuted(SetTaskExecutedRequest request);
}
