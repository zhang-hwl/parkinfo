package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonServiceWindowRepository extends JpaRepository<CommonServiceWindow,String> {
    Page<CommonServiceWindow> findAll(Specification<CommonServiceWindow> serviceWindowSpecification, Pageable pageable);

    Optional<CommonServiceWindow> findByDeleteIsFalseAndId(String id);
}
