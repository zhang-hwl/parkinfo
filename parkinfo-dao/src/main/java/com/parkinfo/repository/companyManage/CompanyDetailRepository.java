package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.CompanyDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyDetailRepository extends JpaRepository<CompanyDetail,String> {

    Page<CompanyDetail> findAll(Specification<CompanyDetail> specification, Pageable pageable);

    Optional<CompanyDetail> findByIdAndDeleteIsFalse(String id);

    Optional<CompanyDetail> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    Optional<CompanyDetail> findByIdAndDeleteEnterIsFalse(String id);
}
