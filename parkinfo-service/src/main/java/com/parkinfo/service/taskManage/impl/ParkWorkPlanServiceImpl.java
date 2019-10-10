package com.parkinfo.service.taskManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.companyManage.CompanyDetail;
import com.parkinfo.entity.taskManage.ParkWorkPlan;
import com.parkinfo.entity.taskManage.WorkPlanDetail;
import com.parkinfo.entity.userConfig.ParkInfo;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.taskManage.ParkWorkPlanRepository;
import com.parkinfo.repository.taskManage.WorkPlanDetailRepository;
import com.parkinfo.request.taskManage.AddParkWorkPlanRequest;
import com.parkinfo.request.taskManage.ExportWorkPlanRequest;
import com.parkinfo.request.taskManage.QueryWorkPlanListRequest;
import com.parkinfo.request.taskManage.SetParkWorkPlanRequest;
import com.parkinfo.response.taskManage.ExportWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.ParkWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.ParkWorkPlanListResponse;
import com.parkinfo.service.taskManage.IParkWorkPlanService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @create 2019-09-12 14:52
 **/
@Service
@Slf4j
public class ParkWorkPlanServiceImpl implements IParkWorkPlanService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ParkWorkPlanRepository parkWorkPlanRepository;

    @Autowired
    private WorkPlanDetailRepository workPlanDetailRepository;

    @Override
    public Result<Page<ParkWorkPlanListResponse>> search(QueryWorkPlanListRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ParkWorkPlan> parkWorkPlanSpecification = (Specification<ParkWorkPlan>) (root, criteriaQuery, criteriaBuilder) -> {
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
            if (currentUser.getRole().contains(ParkRoleEnum.PARK_USER.toString())) {  //普通员工
                predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), currentUser.getCurrentParkId()));
                predicates.add(criteriaBuilder.equal(root.get("author").get("id").as(String.class), currentUser.getId()));
            }
            if (currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())) { //园区管理员
                predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), currentUser.getCurrentParkId()));
            }else {  //总裁
                if (StringUtils.isNotBlank(request.getParkId())){
                    predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), request.getParkId()));
                }
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ParkWorkPlan> parkWorkPlanPage = parkWorkPlanRepository.findAll(parkWorkPlanSpecification, pageable);
        Page<ParkWorkPlanListResponse> responsePage = this.convertParkWorkPlanList(parkWorkPlanPage);
        return Result.<Page<ParkWorkPlanListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<ParkWorkPlanDetailResponse> detail(String id) {
        ParkWorkPlan parkWorkPlan = this.checkParkWorkPlan(id);
        ParkWorkPlanDetailResponse response = this.convertParkWorkPlanDetail(parkWorkPlan);
        return Result.<ParkWorkPlanDetailResponse>builder().success().data(response).build();
    }

    @Override
    @Transactional
    public Result addTask(AddParkWorkPlanRequest request) {
//        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        ParkWorkPlan parkWorkPlan = new ParkWorkPlan();
        BeanUtils.copyProperties(request, parkWorkPlan);
        parkWorkPlan.setAuthor(tokenUtils.getLoginUser());
        ParkInfo parkInfo = tokenUtils.getCurrentParkInfo();
        parkWorkPlan.setParkManager(parkInfo.getManager());
        parkWorkPlan.setStep(2);  //流转下一级
        parkWorkPlan.setAvailable(true);
        parkWorkPlan.setDelete(false);
        parkWorkPlan.setFinished(false);
        parkWorkPlan.setPark(parkInfo);
        parkWorkPlanRepository.save(parkWorkPlan);
        request.getWorkPlanDetailRequestList().forEach(workPlanDetailRequest -> {
            WorkPlanDetail workPlanDetail = new WorkPlanDetail();
            BeanUtils.copyProperties(workPlanDetailRequest, workPlanDetail);
            workPlanDetail.setParkWorkPlan(parkWorkPlan);
            workPlanDetail.setAvailable(true);
            workPlanDetail.setDelete(false);
            workPlanDetailRepository.save(workPlanDetail);
        });
        return Result.builder().success().message("创建成功").build();
    }

    @Override
    @Transactional
    public Result setTask(SetParkWorkPlanRequest request) {
        ParkWorkPlan parkWorkPlan = this.checkParkWorkPlan(request.getId());
        if (request.getAgree()) {
            parkWorkPlan.setStep(parkWorkPlan.getStep() + 1);
            if (parkWorkPlan.getStep() == 4) {
                parkWorkPlan.setFinished(true);
            }
        } else {
            parkWorkPlan.setStep(parkWorkPlan.getStep() - 1);
        }
        BeanUtils.copyProperties(request, parkWorkPlan);
        request.getWorkPlanDetailList().forEach(workPlanDetailRequest -> {
            WorkPlanDetail workPlanDetail;
            if (StringUtils.isNotBlank(workPlanDetailRequest.getId())) {
                workPlanDetail = this.checkWorkPlanDetail(workPlanDetailRequest.getId());
            } else {
                workPlanDetail = new WorkPlanDetail();
            }
            BeanUtils.copyProperties(workPlanDetailRequest, workPlanDetail);
            workPlanDetail.setParkWorkPlan(parkWorkPlan);
            workPlanDetailRepository.save(workPlanDetail);
        });
        return Result.builder().success().message("任务修改成功").build();
    }

    @Override
    public Result deleteTask(String id) {
        ParkWorkPlan parkWorkPlan = this.checkParkWorkPlan(id);
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())) {
            parkWorkPlan.setDelete(true);
            parkWorkPlanRepository.save(parkWorkPlan);
        } else if (currentUser.getId().equals(parkWorkPlan.getAuthor().getId())) {
            parkWorkPlan.setDelete(true);
            parkWorkPlanRepository.save(parkWorkPlan);
        } else {
            throw new NormalException("您无法进行此操作");
        }
        return Result.builder().success().message("删除成功").build();
    }

    @Override
    public void exportWorkPlan(ExportWorkPlanRequest request, HttpServletResponse response) {
        List<WorkPlanDetail> workPlanDetailList = workPlanDetailRepository.findByParkWorkPlan_IdInAndDeleteIsFalseAndAvailableIsTrue(request.getIds());
        List<ExportWorkPlanDetailResponse> workPlanDetailResponseList = this.convertParkWorkPlanWorkPlanDetailToExport(workPlanDetailList);
        try {
            ExcelUtils.exportExcel(workPlanDetailResponseList, "园区工作计划及小节详情", "园区工作计划及小节详情", ExportWorkPlanDetailResponse.class, "temp", response);
        } catch (Exception e) {
            throw new NormalException("园区工作计划及小节详情导出失败");
        }
    }

    private Page<ParkWorkPlanListResponse> convertParkWorkPlanList(Page<ParkWorkPlan> parkWorkPlanPage) {
        List<ParkWorkPlanListResponse> content = Lists.newArrayList();
        parkWorkPlanPage.forEach(parkWorkPlan -> {
            ParkWorkPlanListResponse response = new ParkWorkPlanListResponse();
            BeanUtils.copyProperties(parkWorkPlan, response);
            if (parkWorkPlan.getAuthor() != null) {
                response.setAuthor(parkWorkPlan.getAuthor().getNickname());
            }
            if (parkWorkPlan.getPark() != null) {
                response.setParkName(parkWorkPlan.getPark().getName());
            }
//            List<WorkPlanDetail> detailList = workPlanDetailRepository.findByParkWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(response.getId());
//            response.setWorkPlanDetails(detailList);
            content.add(response);
        });
        return new PageImpl<>(content, parkWorkPlanPage.getPageable(), parkWorkPlanPage.getTotalElements());
    }

    private ParkWorkPlanDetailResponse convertParkWorkPlanDetail(ParkWorkPlan parkWorkPlan) {
        ParkWorkPlanDetailResponse response = new ParkWorkPlanDetailResponse();
        BeanUtils.copyProperties(parkWorkPlan, response);
        if (parkWorkPlan.getAuthor() != null) {
            response.setAuthor(parkWorkPlan.getAuthor().getNickname());
        }
        if (parkWorkPlan.getPark() != null) {
            response.setParkName(parkWorkPlan.getPark().getName());
        }
        List<WorkPlanDetail> detailList = workPlanDetailRepository.findByParkWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(response.getId());
        response.setWorkPlanDetailList(detailList);
        return response;
    }

    private List<ExportWorkPlanDetailResponse> convertParkWorkPlanWorkPlanDetailToExport(List<WorkPlanDetail> workPlanDetailList) {
        List<ExportWorkPlanDetailResponse> responseList = Lists.newArrayList();
        workPlanDetailList.forEach(workPlanDetail -> {
            ExportWorkPlanDetailResponse response = new ExportWorkPlanDetailResponse();
            BeanUtils.copyProperties(workPlanDetail,response);
            if (workPlanDetail.getParkWorkPlan()!=null){
                response.setName(workPlanDetail.getParkWorkPlan().getName());
            }
            responseList.add(response);
        });
        return responseList;
    }

    private ParkWorkPlan checkParkWorkPlan(String id) {
        Optional<ParkWorkPlan> parkWorkPlanOptional = parkWorkPlanRepository.findById(id);
        if (!parkWorkPlanOptional.isPresent()) {
            throw new NormalException("任务不存在");
        }
        return parkWorkPlanOptional.get();
    }

    private WorkPlanDetail checkWorkPlanDetail(String id) {
        Optional<WorkPlanDetail> workPlanDetailOptional = workPlanDetailRepository.findById(id);
        if (!workPlanDetailOptional.isPresent()) {
            throw new NormalException("任务详情不存在");
        }
        return workPlanDetailOptional.get();
    }
}
