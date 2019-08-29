package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.PolicyPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PolicyPaperRepository extends JpaRepository<PolicyPaper, String> {

    Page<PolicyPaper> findAll(Specification<PolicyPaper> staffSalarySpecification , Pageable pageable);

    Optional<PolicyPaper> findById(String id);

    List<PolicyPaper> findAllByPolicyType(String policyType);

}
