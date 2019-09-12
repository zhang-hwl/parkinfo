package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.activityApply.ActivityApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityApplyRepository extends JpaRepository<ActivityApply,String> {
    Page<ActivityApply> findAll(Specification<ActivityApply> specification, Pageable pageable);

    Optional<ActivityApply> findByDeleteIsFalseAndId(String id);
}
