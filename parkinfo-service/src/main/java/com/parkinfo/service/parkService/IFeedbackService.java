package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.request.parkService.feedback.QueryFeedBackRequest;
import com.parkinfo.response.parkService.FeedbackResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFeedbackService {
    /**
     * 新增对运营方意见反馈
     * @param request
     * @return
     */
    Result<String> addFeedback(AddFeedbackRequest request);

    Result<String> deleteFeedback(String id);

    Result<FeedbackResponse> detailFeedback(String id);

    Result<Page<FeedbackResponse>> findAll(QueryFeedBackRequest request);
}
