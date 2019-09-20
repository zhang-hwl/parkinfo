package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.feedback.Feedback;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.FeedbackRepository;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.request.parkService.feedback.QueryFeedBackRequest;
import com.parkinfo.response.parkService.FeedbackResponse;
import com.parkinfo.service.parkService.IFeedbackService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public Result<Page<FeedbackResponse>> findAll(QueryFeedBackRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(),Sort.Direction.DESC, "createTime");
        Specification<Feedback> specification = new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                if(StringUtils.isNotBlank(request.getKeyWords())){
                    predicates.add(cb.like(root.get("questionDescription").as(String.class), "%"+request.getKeyWords()+"%"));
                }
                predicates.add(cb.equal(root.get("delete").as(Boolean.class), false));
                predicates.add(cb.equal(root.get("available").as(Boolean.class), true));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Feedback> all = feedbackRepository.findAll(specification, pageable);
        return Result.<Page<FeedbackResponse>>builder().success().data(null).build();
    }
}
