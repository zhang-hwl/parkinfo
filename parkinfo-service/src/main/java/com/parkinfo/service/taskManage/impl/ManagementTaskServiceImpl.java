package com.parkinfo.service.taskManage.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.dto.ParkUserDTO;
import com.parkinfo.entity.taskManage.ManagementTask;
import com.parkinfo.entity.taskManage.ManagementTask;
import com.parkinfo.entity.taskManage.SpecialTask;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.ParkRoleEnum;
import com.parkinfo.enums.TaskType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.taskManage.ManagementTaskRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.taskManage.AddManagementTaskRequest;
import com.parkinfo.request.taskManage.QueryManagementTaskRequest;
import com.parkinfo.response.taskManage.*;
import com.parkinfo.response.taskManage.ManagementTaskListResponse;
import com.parkinfo.service.taskManage.IManagementTaskService;
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
 * @create 2019-09-16 11:27
 **/
@Slf4j
@Service
public class ManagementTaskServiceImpl implements IManagementTaskService {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ManagementTaskRepository managementTaskRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;
    
    @Override
    public Result<Page<ManagementTaskListResponse>> search(QueryManagementTaskRequest request) {
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<ManagementTask> managementTaskSpecification = (Specification<ManagementTask>) (root, criteriaQuery, criteriaBuilder) -> {
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
                ListJoin<ManagementTask,ParkUser> join= root.join(root.getModel().getList("receivers",ParkUser.class), JoinType.LEFT);
                Predicate p2 = criteriaBuilder.equal(join.get("id").as(String.class),tokenUtils.getLoginUserDTO().getId());
                //这里面的join代表的是parkUser，属于加入进来的部分，而不是链接表的全部结果；
                predicates.add(p2);
            }

            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<ManagementTask> managementTaskPage = managementTaskRepository.findAll(managementTaskSpecification, pageable);
        Page<ManagementTaskListResponse> responsePage = this.convertManagementTaskList(managementTaskPage);
        return Result.<Page<ManagementTaskListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<ManagementTaskDetailResponse> detail(String id) {
        ManagementTask managementTask = this.checkManagementTask(id);
        ManagementTaskDetailResponse response = this.convertManagementTaskDetail(managementTask);
        return Result.<ManagementTaskDetailResponse>builder().success().data(response).build();
    }

    @Override
    public Result addTask(AddManagementTaskRequest request) {
        ManagementTask managementTask = new ManagementTask();
        BeanUtils.copyProperties(request, managementTask);
        managementTask.setFinished(true);
        managementTask.setStep(2);
        managementTask.setAuthor(tokenUtils.getLoginUser());
        managementTask.setPark(tokenUtils.getCurrentParkInfo());
        if (request.getReceiverIds() != null) {
            List<ParkUser> parkUserList = parkUserRepository.findAllById(request.getReceiverIds());
            managementTask.setReceivers(parkUserList);
        }
        managementTask.setDelete(false);
        managementTask.setAvailable(true);
        managementTaskRepository.save(managementTask);
        return Result.builder().success().message("提交成功").build();
    }

    @Override
    public Result deleteTask(String taskId) {
        ManagementTask managementTask = this.checkManagementTask(taskId);
        ParkUserDTO currentUser = tokenUtils.getLoginUserDTO();
        if (currentUser.getRole().contains(ParkRoleEnum.GENERAL_MANAGER.toString())
                || currentUser.getRole().contains(ParkRoleEnum.PRESIDENT.toString())) {
            managementTask.setDelete(true);
            managementTaskRepository.save(managementTask);
        } else if (currentUser.getId().equals(managementTask.getAuthor().getId())) {
            managementTask.setDelete(true);
            managementTaskRepository.save(managementTask);
        } else {
            throw new NormalException("您无法进行此操作");
        }
        return Result.builder().success().message("删除成功").build();
    }

    private Page<ManagementTaskListResponse> convertManagementTaskList(Page<ManagementTask> managementTaskPage) {
        List<ManagementTaskListResponse> content = Lists.newArrayList();
        managementTaskPage.forEach(managementTask -> {
            ManagementTaskListResponse response = new ManagementTaskListResponse();
            BeanUtils.copyProperties(managementTask, response);
            if (managementTask.getAuthor() != null) {
                response.setAuthor(managementTask.getAuthor().getNickname());
            }
            if (managementTask.getPark() != null) {
                response.setParkName(managementTask.getPark().getName());
            }
            if (managementTask.getReceivers() != null) {
                List<ReceiverListResponse> receiverList = Lists.newArrayList();
                managementTask.getReceivers().forEach(receiver -> {
                    ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                    receiverListResponse.setId(receiver.getId());
                    receiverListResponse.setName(receiver.getNickname());
                    receiverList.add(receiverListResponse);
                });
                response.setReceivers(receiverList);
            }
            content.add(response);
        });
        return new PageImpl<>(content, managementTaskPage.getPageable(), managementTaskPage.getTotalElements());
    }

    private ManagementTaskDetailResponse convertManagementTaskDetail(ManagementTask managementTask) {
        ManagementTaskDetailResponse response = new ManagementTaskDetailResponse();
        BeanUtils.copyProperties(managementTask, response);
        if (managementTask.getAuthor() != null) {
            response.setAuthor(managementTask.getAuthor().getNickname());
        }
        if (managementTask.getPark() != null) {
            response.setParkName(managementTask.getPark().getName());
        }
        if (managementTask.getReceivers() != null) {
            List<ReceiverListResponse> receiverList = Lists.newArrayList();
            managementTask.getReceivers().forEach(receiver -> {
                ReceiverListResponse receiverListResponse = new ReceiverListResponse();
                receiverListResponse.setId(receiver.getId());
                receiverListResponse.setName(receiver.getNickname());
                receiverList.add(receiverListResponse);
            });
            response.setReceivers(receiverList);
        }
        return response;
    }


    private ManagementTask checkManagementTask(String id) {
        Optional<ManagementTask> managementTaskOptional = managementTaskRepository.findById(id);
        if (!managementTaskOptional.isPresent()) {
            throw new NormalException("任务不存在");
        }
        return managementTaskOptional.get();

    }
}
