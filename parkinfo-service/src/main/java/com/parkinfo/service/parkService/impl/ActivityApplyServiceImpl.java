package com.parkinfo.service.parkService.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.parkService.activityApply.ActivityApply;
import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkService.ActivityApplyRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.parkService.activityApply.AddActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.EditActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.SearchActivityApplyRequest;
import com.parkinfo.response.parkService.ActivityApplyResponse;
import com.parkinfo.response.parkService.ActivityApplyResponse;
import com.parkinfo.service.parkService.IActivityApplyService;
import com.parkinfo.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityApplyServiceImpl implements IActivityApplyService {
    @Autowired
    private ActivityApplyRepository activityApplyRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<Page<ActivityApplyResponse>> searchActivityApply(SearchActivityApplyRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(),request.getPageSize(), Sort.Direction.DESC,"createTime");
        Specification<ActivityApply> specification = (Specification<ActivityApply>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateLists = Lists.newArrayList();
            if (StringUtils.isNotBlank(request.getActivityName())){
                predicateLists.add(criteriaBuilder.like(root.get("activityName").as(String.class),"%"+request.getActivityName()+"%"));
            }
            predicateLists.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class),Boolean.FALSE));
            predicateLists.add(criteriaBuilder.equal(root.get("available").as(Boolean.class),Boolean.TRUE));
            predicateLists.add(criteriaBuilder.equal(root.get("parkInfo").as(ParkInfo.class),tokenUtils.getCurrentParkInfo()));
            return criteriaBuilder.and(predicateLists.toArray(new Predicate[0]));
        };
        Page<ActivityApply> activityApplyPage = activityApplyRepository.findAll(specification,pageable);
        Page<ActivityApplyResponse> responses = this.convertActivityApplyResponsePage(activityApplyPage);
        return Result.<Page<ActivityApplyResponse>>builder().success().data(responses).build();
    }

    private Page<ActivityApplyResponse> convertActivityApplyResponsePage(Page<ActivityApply> activityApplyPage) {
        List<ActivityApplyResponse> responses = Lists.newArrayList();
        activityApplyPage.getContent().forEach(activityApply -> {
            ActivityApplyResponse response = new ActivityApplyResponse();
            BeanUtils.copyProperties(activityApply,response);
            if (activityApply.getCompanyDetail() != null){
                response.setCompanyName(activityApply.getCompanyDetail().getCompanyName());
            }
            responses.add(response);
        });
        return new PageImpl<>(responses,activityApplyPage.getPageable(),activityApplyPage.getTotalElements());
    }

    @Override
    public Result<String> addActivityApply(AddActivityApplyRequest request) {
        ParkUser loginUser = tokenUtils.getLoginUser();
        ActivityApply activityApply = new ActivityApply();
        BeanUtils.copyProperties(request,activityApply);
//        if (loginUser.getCompanyDetail() != null){
//            activityApply.setCompanyDetail(loginUser.getCompanyDetail());
//        }
        Optional<ParkUser> byId = parkUserRepository.findByIdAndAvailableIsTrueAndDeleteIsFalse(loginUser.getId());
        if(byId.isPresent()){
            ParkUser parkUser = byId.get();
            activityApply.setCompanyDetail(parkUser.getCompanyDetail());
        }
        activityApply.setParkInfo(tokenUtils.getCurrentParkInfo());
        activityApply.setDelete(Boolean.FALSE);
        activityApply.setAvailable(Boolean.TRUE);
        activityApplyRepository.save(activityApply);
        return Result.<String>builder().success().message("新增成功").build();
    }

    @Override
    public Result<String> editActivityApply(EditActivityApplyRequest request) {
        ActivityApply activityApply = this.checkActivityApply(request.getId());
        activityApply.setActivityDescription(request.getActivityDescription());
        activityApply.setActivityName(request.getActivityName());
        activityApply.setActivityTime(request.getActivityTime());
        activityApply.setContactNumber(request.getContactNumber());
        activityApply.setContacts(request.getContacts());
        activityApplyRepository.save(activityApply);
        return Result.<String>builder().success().message("编辑成功").build();
    }

    private ActivityApply checkActivityApply(String id) {
        Optional<ActivityApply> activityApplyOptional = activityApplyRepository.findByDeleteIsFalseAndId(id);
        if (!activityApplyOptional.isPresent()){
            throw new NormalException("活动申请不存在");
        }
        return activityApplyOptional.get();
    }

    @Override
    public Result<String> deleteActivityApply(String id) {
        ActivityApply activityApply = this.checkActivityApply(id);
        activityApply.setDelete(Boolean.TRUE);
        activityApplyRepository.save(activityApply);
        return Result.<String>builder().success().message("删除成功").build();
    }

    @Override
    public Result<ActivityApplyResponse> detailActivityApply(String id) {
        ActivityApply activityApply = this.checkActivityApply(id);
        ActivityApplyResponse response = new ActivityApplyResponse();
        BeanUtils.copyProperties(activityApply, response);
        return Result.<ActivityApplyResponse>builder().success().data(response).build();
    }
}
