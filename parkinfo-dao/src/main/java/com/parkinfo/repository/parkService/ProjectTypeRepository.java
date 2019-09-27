package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.projectApply.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ProjectTypeRepository extends JpaRepository<ProjectType,String>, JpaSpecificationExecutor<ProjectType> {
    Optional<ProjectType> findByDeleteIsFalseAndId(String id);

    List<ProjectType> findAllByDeleteIsFalseAndAvailableIsTrue();
}
