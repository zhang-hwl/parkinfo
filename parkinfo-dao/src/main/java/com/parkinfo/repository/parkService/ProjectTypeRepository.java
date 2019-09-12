package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.projectApply.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectTypeRepository extends JpaRepository<ProjectType,String> {
    Optional<ProjectType> findByDeleteIsFalseAndId(String id);
}
