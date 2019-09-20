package com.parkinfo.repository.personalCloud;

import com.parkinfo.entity.personalCloud.CloudDisk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CloudDiskRepository extends JpaRepository<CloudDisk,String> {

    Page<CloudDisk> findAll(Specification<CloudDisk> specification, Pageable pageable);

    Optional<CloudDisk> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);
}
