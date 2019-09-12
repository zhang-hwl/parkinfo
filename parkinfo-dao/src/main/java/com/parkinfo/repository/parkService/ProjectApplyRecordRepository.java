package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.projectApply.ProjectApplyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectApplyRecordRepository extends JpaRepository<ProjectApplyRecord,String> {
    List<ProjectApplyRecord> findByDeleteIsFalseAndProjectInfo_Id(String projectId);

    List<ProjectApplyRecord> findByDeleteIsFalseAndCompanyDetail_ParkUser_Id(String userId);

    Optional<ProjectApplyRecord> findByDeleteIsFalseAndId(String id);
}
