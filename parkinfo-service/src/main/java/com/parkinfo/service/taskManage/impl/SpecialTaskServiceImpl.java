package com.parkinfo.service.taskManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.taskManage.SpecialTask;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.enums.TaskType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.taskManage.SpecialTaskRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.taskManage.AddSpecialTaskRequest;
import com.parkinfo.request.taskManage.QuerySpecialTaskRequest;
import com.parkinfo.response.taskManage.ReceiverListResponse;
import com.parkinfo.response.taskManage.SpecialTaskDetailResponse;
import com.parkinfo.response.taskManage.SpecialTaskListResponse;
import com.parkinfo.service.taskManage.ISpecialTaskService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 16:36
 **/
@Service
@Slf4j
public class SpecialTaskServiceImpl implements ISpecialTaskService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private SpecialTaskRepository specialTaskRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Override
    public Result<Page<SpecialTaskListResponse>> search(QuerySpecialTaskRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<SpecialTask> specialTaskSpecification = (Specification<SpecialTask>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getStartTimeFrom() != null && request.getStartTimeTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("startTime"), request.getStartTimeFrom(), request.getStartTimeTo()));
            }
            if (request.getEndTimeFrom() != null && request.getEndTimeTo() != null) {
                predicates.add(criteriaBuilder.between(root.get("endTime"), request.getEndTimeFrom(), request.getEndTimeTo()));
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
                Path<Object> path = root.get("receiver");  //接收的
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                in.value(tokenUtils.getLoginUser());
                predicates.add(criteriaBuilder.and(in));
            }

            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<SpecialTask> specialTaskPage = specialTaskRepository.findAll(specialTaskSpecification, pageable);
        Page<SpecialTaskListResponse> responsePage = this.convertSpecialTaskList(specialTaskPage);
        return Result.<Page<SpecialTaskListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<SpecialTaskDetailResponse> detail(String id) {
        SpecialTask specialTask = this.checkSpecialTask(id);
        SpecialTaskDetailResponse response = this.convertSpecialTaskDetail(specialTask);
        return Result.<SpecialTaskDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result addTask(AddSpecialTaskRequest request) {
        SpecialTask specialTask = new SpecialTask();
        BeanUtils.copyProperties(request, specialTask);
        specialTask.setFinished(true);
        specialTask.setStep(2);
        specialTask.setAuthor(tokenUtils.getLoginUser());
        specialTask.setPark(tokenUtils.getCurrentParkInfo());
        if (request.getReceiverIds() != null) {
            List<ParkUser> parkUserList = parkUserRepository.findAllById(request.getReceiverIds());
            specialTask.setReceivers(parkUserList);
        }
        specialTask.setDelete(false);
        specialTask.setAvailable(true);
        specialTaskRepository.save(specialTask);
        return Result.builder().success().message("提交成功").build();
    }

    @Override
    public Result deleteTask(String taskId) {
        SpecialTask specialTask = this.checkSpecialTask(taskId);
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString())) {
            specialTask.setDelete(true);
            specialTaskRepository.save(specialTask);
        } else if (currentUser.getId().equals(specialTask.getAuthor().getId())) {
            specialTask.setDelete(true);
            specialTaskRepository.save(specialTask);
        } else {
            throw new NormalException("您无法进行此操作");
        }
        return Result.builder().success().message("删除成功").build();
    }

    private Page<SpecialTaskListResponse> convertSpecialTaskList(Page<SpecialTask> specialTaskPage) {
        List<SpecialTaskListResponse> content = Lists.newArrayList();
        specialTaskPage.forEach(specialTask -> {
            SpecialTaskListResponse response = new SpecialTaskListResponse();
            BeanUtils.copyProperties(specialTask, response);
            if (specialTask.getAuthor() != null) {
                response.setAuthor(specialTask.getAuthor().getNickname());
            }
            if (specialTask.getPark() != null) {
                response.setParkName(specialTask.getPark().getName());
            }
            if (specialTask.getReceivers() != null) {
                List<ReceiverListResponse> receiverList = Lists.newArrayList();
                specialTask.getReceivers().forEach(receiver -> {
                    ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                    receiverListResponse.setId(receiver.getId());
                    receiverListResponse.setNickname(receiver.getNickname());
                    receiverList.add(receiverListResponse);
                });
                response.setReceivers(receiverList);
            }
            content.add(response);
        });
        return new PageImpl<>(content, specialTaskPage.getPageable(), specialTaskPage.getTotalElements());
    }

    private SpecialTaskDetailResponse convertSpecialTaskDetail(SpecialTask specialTask) {
        SpecialTaskDetailResponse response = new SpecialTaskDetailResponse();
        BeanUtils.copyProperties(specialTask, response);
        if (specialTask.getAuthor() != null) {
            response.setAuthor(specialTask.getAuthor().getNickname());
        }
        if (specialTask.getPark() != null) {
            response.setParkName(specialTask.getPark().getName());
        }
        if (specialTask.getReceivers() != null) {
            List<ReceiverListResponse> receiverList = Lists.newArrayList();
            specialTask.getReceivers().forEach(receiver -> {
                ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                receiverListResponse.setId(receiver.getId());
                receiverListResponse.setNickname(receiver.getNickname());
                receiverList.add(receiverListResponse);
            });
            response.setReceivers(receiverList);
        }
        return response;
    }


    private SpecialTask checkSpecialTask(String id) {
        Optional<SpecialTask> specialTaskOptional = specialTaskRepository.findById(id);
        if (!specialTaskOptional.isPresent()) {
            throw new NormalException("任务不存在");
        }
        return specialTaskOptional.get();

    }
}
