package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.PersonalWorkPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalWorkPlanRepository extends JpaRepository<PersonalWorkPlan,String> {
    Page<PersonalWorkPlan> findAll(Specification<PersonalWorkPlan> personalWorkPlanSpecification, Pageable pageable);
}
