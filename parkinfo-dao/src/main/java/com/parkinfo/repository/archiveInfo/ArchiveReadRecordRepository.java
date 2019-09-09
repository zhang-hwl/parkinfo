package com.parkinfo.repository.archiveInfo;

import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ArchiveReadRecordRepository extends JpaRepository<ArchiveReadRecord, String>,JpaSpecificationExecutor<ArchiveReadRecord> {

    Page<ArchiveReadRecord> findAll(Specification<ArchiveReadRecord> specification, Pageable pageable);


}
