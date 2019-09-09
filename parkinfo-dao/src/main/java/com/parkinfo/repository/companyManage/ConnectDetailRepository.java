package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.ConnectDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectDetailRepository extends JpaRepository<ConnectDetail,String> {

    Optional<ConnectDetail> findByCompanyDetail_IdAndDeleteIsFalseAndAvailableIsTrue(String id);
}
