package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessAmuseRepository extends JpaRepository<BusinessAmuse,String> {
    Page<BusinessAmuse> findAll(Specification<BusinessAmuse> specification, Pageable pageable);

    Optional<BusinessAmuse> findByDeleteIsFalseAndId(String id);
}
