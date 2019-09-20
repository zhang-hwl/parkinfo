package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.enums.EnclosureType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnclosureTotalRepository extends JpaRepository<EnclosureTotal,String> {
    List<EnclosureTotal> findAllByCompanyDetail_IdAndDeleteIsFalse(String id);

    Optional<EnclosureTotal> findByEnclosureTypeAndAndDeleteIsFalse(EnclosureType enclosureType);
}
