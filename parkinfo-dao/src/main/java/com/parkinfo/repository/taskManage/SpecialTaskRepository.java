package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.SpecialTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialTaskRepository extends JpaRepository<SpecialTask,String> {
    Page<SpecialTask> findAll(Specification<SpecialTask> specialTaskSpecification, Pageable pageable);
}
