package com.parkinfo.service.taskManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.taskManage.PersonalWorkPlan;
import com.parkinfo.entity.taskManage.WorkPlanDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.taskManage.PersonalWorkPlanRepository;
import com.parkinfo.repository.taskManage.WorkPlanDetailRepository;
import com.parkinfo.request.taskManage.AddPersonalWorkPlanRequest;
import com.parkinfo.request.taskManage.ExportWorkPlanRequest;
import com.parkinfo.request.taskManage.QueryPersonalPlanListRequest;
import com.parkinfo.request.taskManage.SetPersonalWorkPlanRequest;
import com.parkinfo.response.taskManage.ExportWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanListResponse;
import com.parkinfo.service.taskManage.IPersonalWorkPlanService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 15:54
 **/
@Slf4j
@Service
public class PersonalWorkPlanServiceImpl implements IPersonalWorkPlanService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PersonalWorkPlanRepository personalWorkPlanRepository;

    @Autowired
    private WorkPlanDetailRepository workPlanDetailRepository;


    @Override
    public Result<Page<PersonalWorkPlanListResponse>> search(QueryPersonalPlanListRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<PersonalWorkPlan> personalWorkPlanSpecification = (Specification<PersonalWorkPlan>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
            if (request.getPlanType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("planType"), request.getPlanType()));
            }
            if (request.getCreateTimeFrom() != null && request.getCreateTimeTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("createTime"), request.getCreateTimeFrom(), request.getCreateTimeTo()));
            }
            predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), currentUser.getCurrentParkId()));
            if (currentUser.getRole().contains(ParkRoleEnum.PARK_USER.toString())) {  //普通员工
                predicates.add(criteriaBuilder.equal(root.get("author").get("id").as(String.class), currentUser.getId()));

            }
//            else {
//                if (StringUtils.isNotBlank(request.getParkId())){
//                    predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), request.getParkId()));
//                }
//            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<PersonalWorkPlan> parkWorkPlanPage = personalWorkPlanRepository.findAll(personalWorkPlanSpecification, pageable);
        Page<PersonalWorkPlanListResponse> responsePage = this.convertPersonalWorkPlanList(parkWorkPlanPage);
        return Result.<Page<PersonalWorkPlanListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<PersonalWorkPlanDetailResponse> detail(String id) {
        PersonalWorkPlan personalWorkPlan = this.checkPersonalWorkPlan(id);
        PersonalWorkPlanDetailResponse response = this.convertPersonalWorkPlanWorkPlanDetail(personalWorkPlan);
        return Result.<PersonalWorkPlanDetailResponse>builder().success().data(response).build();
    }


    @Override
    public Result addTask(AddPersonalWorkPlanRequest request) {
        PersonalWorkPlan personalWorkPlan = new PersonalWorkPlan();
        BeanUtils.copyProperties(request, personalWorkPlan);
        personalWorkPlan.setAuthor(tokenUtils.getLoginUser());
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        personalWorkPlan.setPark(parkInfo);
        personalWorkPlan.setStep(2);  //流转下一级
        personalWorkPlan.setAvailable(true);
        personalWorkPlan.setDelete(false);
        personalWorkPlanRepository.save(personalWorkPlan);
        request.getWorkPlanDetailRequestList().forEach(workPlanDetailRequest -> {
            WorkPlanDetail workPlanDetail = new WorkPlanDetail();
            BeanUtils.copyProperties(workPlanDetailRequest, workPlanDetail);
            workPlanDetail.setPersonalWorkPlan(personalWorkPlan);
            workPlanDetail.setAvailable(true);
            workPlanDetail.setDelete(false);
            workPlanDetailRepository.save(workPlanDetail);
        });
        return Result.builder().success().message("创建成功").build();
    }

    @Override
    public Result setTask(SetPersonalWorkPlanRequest request) {
        PersonalWorkPlan personalWorkPlan = this.checkPersonalWorkPlan(request.getId());
        if (request.getAgree()) {
            if (personalWorkPlan.getStep() == 4) {
                personalWorkPlan.setFinished(true);
            }else {
                personalWorkPlan.setStep(personalWorkPlan.getStep() + 1);
            }
        } else {
            personalWorkPlan.setStep(personalWorkPlan.getStep() - 1);
        }
        BeanUtils.copyProperties(request, personalWorkPlan);
        request.getWorkPlanDetailList().forEach(workPlanDetailRequest -> {
            WorkPlanDetail workPlanDetail;
            if (StringUtils.isNotBlank(workPlanDetailRequest.getId())) {
                workPlanDetail = this.checkWorkPlanDetail(workPlanDetailRequest.getId());
                BeanUtils.copyProperties(workPlanDetailRequest, workPlanDetail);
            } else {
                workPlanDetail = new WorkPlanDetail();
                BeanUtils.copyProperties(workPlanDetailRequest, workPlanDetail);
                workPlanDetail.setDelete(false);
                workPlanDetail.setAvailable(true);
            }
            workPlanDetail.setPersonalWorkPlan(personalWorkPlan);
            workPlanDetailRepository.save(workPlanDetail);
        });
        return Result.builder().success().message("任务修改成功").build();
    }

    @Override
    public Result deleteTask(String id) {
        PersonalWorkPlan personalWorkPlan = this.checkPersonalWorkPlan(id);
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())) {
            personalWorkPlan.setDelete(true);
            personalWorkPlanRepository.save(personalWorkPlan);
        } else if (currentUser.getId().equals(personalWorkPlan.getAuthor().getId())) {
            personalWorkPlan.setDelete(true);
            personalWorkPlanRepository.save(personalWorkPlan);
        } else {
            throw new NormalException("您无法进行此操作");
        }
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public void exportWorkPlan(ExportWorkPlanRequest request, HttpServletResponse response) {
        List<WorkPlanDetail> workPlanDetailList = workPlanDetailRepository.findByPersonalWorkPlan_IdInAndDeleteIsFalseAndAvailableIsTrue(request.getIds());
        List<ExportWorkPlanDetailResponse> workPlanDetailResponseList = this.convertPersonalWorkPlanWorkPlanDetailToExport(workPlanDetailList);
        try {
            ExcelUtils.exportExcel(workPlanDetailResponseList, "个人工作计划及小节详情", "个人工作计划及小节详情", ExportWorkPlanDetailResponse.class, "temp", response);
        } catch (Exception e) {
            throw new NormalException("个人工作计划及小节详情导出失败");
        }
    }



    private Page<PersonalWorkPlanListResponse> convertPersonalWorkPlanList(Page<PersonalWorkPlan> personalWorkPlanPage) {
        List<PersonalWorkPlanListResponse> content = Lists.newArrayList();
        personalWorkPlanPage.forEach(personalWorkPlan -> {
            PersonalWorkPlanListResponse response = new PersonalWorkPlanListResponse();
            BeanUtils.copyProperties(personalWorkPlan, response);
            if (personalWorkPlan.getAuthor() != null) {
                response.setAuthor(personalWorkPlan.getAuthor().getNickname());
            }
            if (personalWorkPlan.getPark() != null) {
                response.setParkName(personalWorkPlan.getPark().getName());
            }
//            List<WorkPlanDetail> detailList = workPlanDetailRepository.findByParkWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(response.getId());
//            response.setWorkPlanDetails(detailList);
            content.add(response);
        });
        return new PageImpl<>(content, personalWorkPlanPage.getPageable(), personalWorkPlanPage.getTotalElements());

    }
    private PersonalWorkPlanDetailResponse convertPersonalWorkPlanWorkPlanDetail(PersonalWorkPlan personalWorkPlan) {
        PersonalWorkPlanDetailResponse response = new PersonalWorkPlanDetailResponse();
        BeanUtils.copyProperties(personalWorkPlan, response);
        if (personalWorkPlan.getAuthor() != null) {
            response.setAuthor(personalWorkPlan.getAuthor().getNickname());
        }
        if (personalWorkPlan.getPark() != null) {
            response.setParkName(personalWorkPlan.getPark().getName());
        }
        List<WorkPlanDetail> detailList = workPlanDetailRepository.findByPersonalWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(response.getId());
        response.setWorkPlanDetailList(detailList);
        return response;
    }

    private List<ExportWorkPlanDetailResponse> convertPersonalWorkPlanWorkPlanDetailToExport(List<WorkPlanDetail> workPlanDetailList) {
        List<ExportWorkPlanDetailResponse> responseList = Lists.newArrayList();
        workPlanDetailList.forEach(workPlanDetail -> {
            ExportWorkPlanDetailResponse response = new ExportWorkPlanDetailResponse();
            BeanUtils.copyProperties(workPlanDetail,response);
            if (workPlanDetail.getPersonalWorkPlan()!=null){
                response.setName(workPlanDetail.getPersonalWorkPlan().getName());
            }
            responseList.add(response);
        });
        return responseList;
    }

    private PersonalWorkPlan checkPersonalWorkPlan(String id) {
        Optional<PersonalWorkPlan> personalWorkPlanOptional = personalWorkPlanRepository.findById(id);
        if (!personalWorkPlanOptional.isPresent()) {
            throw new NormalException("任务不存在");
        }
        return personalWorkPlanOptional.get();
    }

    private WorkPlanDetail checkWorkPlanDetail(String id) {
        Optional<WorkPlanDetail> workPlanDetailOptional = workPlanDetailRepository.findById(id);
        if (!workPlanDetailOptional.isPresent()) {
            throw new NormalException("任务详情不存在");
        }
        return workPlanDetailOptional.get();
    }

}
