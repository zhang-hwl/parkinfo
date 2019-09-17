package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.feedback.Feedback;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.FeedbackRepository;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.response.parkService.FeedbackResponse;
import com.parkinfo.service.parkService.IFeedbackService;
import com.parkinfo.token.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Result<String> deleteFeedback(String id) {
        Optional<Feedback> byId = feedbackRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byId.isPresent()){
            throw new NormalException("意见不存在");
        }
        feedbackRepository.delete(byId.get());
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result<FeedbackResponse> detailFeedback(String id) {
        Optional<Feedback> byId = feedbackRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(id);
        if(!byId.isPresent()){
            throw new NormalException("意见不存在");
        }
        Feedback feedback = byId.get();
        FeedbackResponse response = new FeedbackResponse();
        BeanUtils.copyProperties(feedback, response);
        return Result.<FeedbackResponse>builder().success().data(response).build();
    }

    @Override
    public Result<List<FeedbackResponse>> findAll() {
        List<Feedback> list = feedbackRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<FeedbackResponse> result = Lists.newArrayList();
        list.forEach(temp -> {
            FeedbackResponse response = new FeedbackResponse();
            BeanUtils.copyProperties(temp, response);
            result.add(response);
        });
        return Result.<List<FeedbackResponse>>builder().success().data(result).build();
    }
}
