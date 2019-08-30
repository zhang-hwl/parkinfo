package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArchiveInfoRepository<T extends ArchiveInfo> extends JpaRepository<T, String> {

    Page<T> findAll(Specification<ArchiveInfo> staffSalarySpecification, Pageable pageable);

    Optional<T> findById(String id);

    List<T> findAllByPolicyType(String policyType);

}
