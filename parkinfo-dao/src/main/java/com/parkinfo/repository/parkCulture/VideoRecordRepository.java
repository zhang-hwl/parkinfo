package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.VideoRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRecordRepository extends JpaRepository<VideoRecord,String> {
    Page<VideoRecord> findAll(Specification<VideoRecord> videoRecordSpecification, Pageable pageable);
}
