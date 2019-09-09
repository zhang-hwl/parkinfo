package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.DiscussDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscussDetailRepository extends JpaRepository<DiscussDetail,String> {

    Optional<DiscussDetail> findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(String id);
}
