package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveInfoTypeRepository extends JpaRepository<ArchiveInfoType, String> {

    Optional<ArchiveInfoType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

}
