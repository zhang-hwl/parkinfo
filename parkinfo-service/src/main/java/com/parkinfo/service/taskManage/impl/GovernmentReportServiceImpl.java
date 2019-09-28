package com.parkinfo.service.taskManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.taskManage.GovernmentReport;
import com.parkinfo.entity.taskManage.SpecialTask;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.enums.TaskType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.taskManage.GovernmentReportRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.taskManage.AddGovernmentReportRequest;
import com.parkinfo.request.taskManage.QueryGovernmentReportRequest;
import com.parkinfo.response.taskManage.GovernmentReportDetailResponse;
import com.parkinfo.response.taskManage.GovernmentReportListResponse;
import com.parkinfo.response.taskManage.ReceiverListResponse;
import com.parkinfo.service.taskManage.IGovernmentReportService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 13:39
 **/
@Service
@Slf4j
public class GovernmentReportServiceImpl implements IGovernmentReportService {


    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private GovernmentReportRepository governmentReportRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<Page<GovernmentReportListResponse>> search(QueryGovernmentReportRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<GovernmentReport> governmentReportSpecification = (Specification<GovernmentReport>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getCreateTimeFrom() != null && request.getCreateTimeTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("createTime"), request.getCreateTimeFrom(), request.getCreateTimeTo()));
            }
            if (StringUtils.isNotBlank(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + request.getName() + "%"));
            }
            if (request.getTaskType().equals(TaskType.sender)) {  //发起的
                if (currentUser.getRole().contains(ParkRoleEnum.PARK_MANAGER.toString())) { //园区管理员
                    predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), currentUser.getCurrentParkId()));
                } else {  //总裁
                    if (StringUtils.isNotBlank(request.getParkId())) {
                        predicates.add(criteriaBuilder.equal(root.get("park").get("id").as(String.class), request.getParkId()));
                    }
                }
            } else {
                ListJoin<GovernmentReport,ParkUser> join= root.join(root.getModel().getList("receivers",ParkUser.class), JoinType.LEFT);
                Predicate p2 = criteriaBuilder.equal(join.get("id").as(String.class),tokenUtils.getLoginUserDTO().getId());
                //这里面的join代表的是parkUser，属于加入进来的部分，而不是链接表的全部结果；
                predicates.add(p2);
            }

            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<GovernmentReport> governmentReportPage = governmentReportRepository.findAll(governmentReportSpecification, pageable);
        Page<GovernmentReportListResponse> responsePage = this.convertGovernmentReportList(governmentReportPage);
        return Result.<Page<GovernmentReportListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<GovernmentReportDetailResponse> detail(String id) {
        GovernmentReport governmentReport = this.checkGovernmentReport(id);
        GovernmentReportDetailResponse response = this.convertGovernmentReportDetail(governmentReport);
        return Result.<GovernmentReportDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result addTask(AddGovernmentReportRequest request) {
        GovernmentReport governmentReport = new GovernmentReport();
        BeanUtils.copyProperties(request, governmentReport);
        governmentReport.setFinished(true);
        governmentReport.setStep(2);
        governmentReport.setAuthor(tokenUtils.getLoginUser());
        governmentReport.setPark(tokenUtils.getCurrentParkInfo());
        if (request.getReceiverIds() != null) {
            List<ParkUser> parkUserList = parkUserRepository.findAllById(request.getReceiverIds());
            governmentReport.setReceivers(parkUserList);
        }
        governmentReport.setDelete(false);
        governmentReport.setAvailable(true);
        governmentReportRepository.save(governmentReport);
        return Result.builder().success().message("提交成功").build();
    }

    @Override
    public Result deleteTask(String taskId) {
        GovernmentReport governmentReport = this.checkGovernmentReport(taskId);
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString())) {
            governmentReport.setDelete(true);
            governmentReportRepository.save(governmentReport);
        } else if (currentUser.getId().equals(governmentReport.getAuthor().getId())) {
            governmentReport.setDelete(true);
            governmentReportRepository.save(governmentReport);
        } else {
            throw new NormalException("您无法进行此操作");
        }
        return Result.builder().success().message("删除成功").build();
    }

    private Page<GovernmentReportListResponse> convertGovernmentReportList(Page<GovernmentReport> governmentReportPage) {
        List<GovernmentReportListResponse> content = Lists.newArrayList();
        governmentReportPage.forEach(governmentReport -> {
            GovernmentReportListResponse response = new GovernmentReportListResponse();
            BeanUtils.copyProperties(governmentReport, response);
            if (governmentReport.getAuthor() != null) {
                response.setAuthor(governmentReport.getAuthor().getNickname());
            }
            if (governmentReport.getPark() != null) {
                response.setParkName(governmentReport.getPark().getName());
            }
            if (governmentReport.getReceivers() != null) {
                List<ReceiverListResponse> receiverList = Lists.newArrayList();
                governmentReport.getReceivers().forEach(receiver -> {
                    ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                    receiverListResponse.setId(receiver.getId());
                    receiverListResponse.setName(receiver.getNickname());
                    receiverList.add(receiverListResponse);
                });
                response.setReceivers(receiverList);
            }
            content.add(response);
        });
        return new PageImpl<>(content, governmentReportPage.getPageable(), governmentReportPage.getTotalElements());
    }

    private GovernmentReportDetailResponse convertGovernmentReportDetail(GovernmentReport governmentReport) {
        GovernmentReportDetailResponse response = new GovernmentReportDetailResponse();
        BeanUtils.copyProperties(governmentReport, response);
        if (governmentReport.getAuthor() != null) {
            response.setAuthor(governmentReport.getAuthor().getNickname());
        }
        if (governmentReport.getPark() != null) {
            response.setParkName(governmentReport.getPark().getName());
        }
        if (governmentReport.getReceivers() != null) {
            List<ReceiverListResponse> receiverList = Lists.newArrayList();
            governmentReport.getReceivers().forEach(receiver -> {
                ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                receiverListResponse.setId(receiver.getId());
                receiverListResponse.setName(receiver.getNickname());
                receiverList.add(receiverListResponse);
            });
            response.setReceivers(receiverList);
        }
        return response;
    }


    private GovernmentReport checkGovernmentReport(String id) {
        Optional<GovernmentReport> governmentReportOptional = governmentReportRepository.findById(id);
        if (!governmentReportOptional.isPresent()) {
            throw new NormalException("任务不存在");
        }
        return governmentReportOptional.get();

    }
}
