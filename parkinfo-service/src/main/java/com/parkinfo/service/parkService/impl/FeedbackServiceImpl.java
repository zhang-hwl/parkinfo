package com.parkinfo.service.parkService.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.feedback.Feedback;
import com.parkinfo.repository.parkService.FeedbackRepository;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.service.parkService.IFeedbackService;
import com.parkinfo.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<String> addFeedback(AddFeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setImgPath(request.getImgPath());
        feedback.setOperatorName(request.getOperatorName());
        feedback.setParkInfo(tokenUtils.getCurrentParkInfo());
        feedback.setQuestionDescription(request.getQuestionDescription());
        feedback.setDelete(Boolean.FALSE);
        feedback.setAvailable(Boolean.TRUE);
        feedbackRepository.save(feedback);
        return Result.<String>builder().success().message("新增成功").build();
    }
}
