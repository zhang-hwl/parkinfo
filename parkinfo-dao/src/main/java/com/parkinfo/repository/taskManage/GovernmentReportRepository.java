package com.parkinfo.repository.taskManage;

import com.parkinfo.entity.taskManage.GovernmentReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GovernmentReportRepository extends JpaRepository<GovernmentReport,String> {
    Page<GovernmentReport> findAll(Specification<GovernmentReport> governmentReportSpecification, Pageable pageable);
}
