package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.CompanyDemand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyDemandRepository extends JpaRepository<CompanyDemand,String> {

    Page<CompanyDemand> findAll(Specification<CompanyDemand> specification, Pageable pageable);

    Optional<CompanyDemand> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<CompanyDemand> findAllByDeleteIsFalseAndAvailableIsTrueAndParkInfo_IdAndIdIsIn(String parkId, List<String> ids);

    List<CompanyDemand> findAllByDeleteIsFalseAndAvailableIsTrueAndIdIn(List<String> ids);
}
