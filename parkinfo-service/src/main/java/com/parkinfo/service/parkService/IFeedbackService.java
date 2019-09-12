package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;

public interface IFeedbackService {
    /**
     * 新增对运营方意见反馈
     * @param request
     * @return
     */
    Result<String> addFeedback(AddFeedbackRequest request);
}
