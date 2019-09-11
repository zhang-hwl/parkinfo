package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.EnclosureTotal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnclosureTotalRepository extends JpaRepository<EnclosureTotal,String> {
    List<EnclosureTotal> findAllByCompanyDetail_IdAndDeleteIsFalse(String id);
}
