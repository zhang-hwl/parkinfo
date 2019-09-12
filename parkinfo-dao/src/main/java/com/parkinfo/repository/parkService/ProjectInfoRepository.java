package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.projectApply.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectInfoRepository extends JpaRepository<ProjectInfo,String> {
    Page<ProjectInfo> findAll(Specification<ProjectInfo> specification, Pageable pageable);

    Optional<ProjectInfo> findByDeleteIsFalseAndId(String id);
}
