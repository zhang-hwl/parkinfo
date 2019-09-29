package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.CompanyDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyDetailRepository extends JpaRepository<CompanyDetail,String> {

    Page<CompanyDetail> findAll(Specification<CompanyDetail> specification, Pageable pageable);

    Optional<CompanyDetail> findByIdAndDeleteIsFalse(String id);

    Optional<CompanyDetail> findByDeleteIsFalseAndParkUser_Id(String userId);

    Optional<CompanyDetail> findByParkUser_IdAndDeleteIsFalse(String userId);

    Optional<CompanyDetail> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    Optional<CompanyDetail> findByIdAndDeleteEnterIsFalse(String id);

//    @Query(nativeQuery = true, value = "select count(id) from c_company_detail where park_id=?1 and entered=?2")
//    Integer findByParkInfo_IdAndEnteredIsTrue(String id, String entered);

    List<CompanyDetail> findByParkInfo_IdAndEnteredIsTrue(String id);

    List<CompanyDetail> findByParkInfo_Id(String id);

    List<CompanyDetail> findByParkUser_Id(String id);

}
