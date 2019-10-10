package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.WorkPlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkPlanDetailRepository extends JpaRepository<WorkPlanDetail,String> {

    List<WorkPlanDetail> findByParkWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<WorkPlanDetail> findByPersonalWorkPlan_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<WorkPlanDetail> findByPersonalWorkPlan_IdInAndDeleteIsFalseAndAvailableIsTrue(List<String> id);

    List<WorkPlanDetail> findByParkWorkPlan_IdInAndDeleteIsFalseAndAvailableIsTrue(List<String> ids);
}
