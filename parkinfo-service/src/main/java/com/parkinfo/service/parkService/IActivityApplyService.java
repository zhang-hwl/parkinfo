package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.activityApply.AddActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.EditActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.SearchActivityApplyRequest;
import com.parkinfo.response.parkService.ActivityApplyResponse;
import org.springframework.data.domain.Page;

public interface IActivityApplyService {
    /**
     * 分页获取活动申请
     * @param request
     * @return
     */
    Result<Page<ActivityApplyResponse>> searchActivityApply(SearchActivityApplyRequest request);

    /**
     * 新增活动申请
     * @param request
     * @return
     */
    Result<String> addActivityApply(AddActivityApplyRequest request);

    /**
     * 编辑活动申请
     * @param request
     * @return
     */
    Result<String> editActivityApply(EditActivityApplyRequest request);

    /**
     * 删除活动申请
     * @param id
     * @return
     */
    Result<String> deleteActivityApply(String id);
}
