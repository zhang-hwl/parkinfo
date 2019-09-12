package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.ParkWorkPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkWorkPlanRepository extends JpaRepository<ParkWorkPlan,String> {
    Page<ParkWorkPlan> findAll(Specification<ParkWorkPlan> parkWorkPlanSpecification, Pageable pageable);
}
