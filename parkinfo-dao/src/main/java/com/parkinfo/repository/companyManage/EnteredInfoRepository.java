package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.EnteredInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnteredInfoRepository extends JpaRepository<EnteredInfo,String> {
    List<EnteredInfo> findAllByCompanyDetail_IdAndDeleteIsFalse(String id);

    Optional<EnteredInfo> findByIdAndDeleteIsFalse(String id);

    void deleteByCompanyDetail_Id(String id);

    List<EnteredInfo> findAllByCompanyDetail_Id(String id);

}
