package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ArchiveInfoRepository extends JpaRepository<ArchiveInfo, String>, JpaSpecificationExecutor<ArchiveInfo> {

    Optional<ArchiveInfo> findByIdAndDeleteIsFalse(String id);

    Page<ArchiveInfo> findAll(Specification<ArchiveInfo> specification, Pageable pageable);

    List<ArchiveInfo> findAll(Specification<ArchiveInfo> specification);

    List<ArchiveInfo> findAllByDeleteIsFalse();

    List<ArchiveInfo> findAllByGeneralIdAndDeleteIsFalse(String id);

}
