package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.ManagementTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementTaskRepository extends JpaRepository<ManagementTask,String> {
    Page<ManagementTask> findAll(Specification<ManagementTask> managementTaskSpecification, Pageable pageable);
}
