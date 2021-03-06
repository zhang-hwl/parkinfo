package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BusinessAmuseTypeRepository extends JpaRepository<BusinessAmuseType,String>, JpaSpecificationExecutor<BusinessAmuseType> {

    List<BusinessAmuseType> findByParentAndDeleteIsFalseAndAvailableIsTrue(BusinessAmuseType type);

    Optional<BusinessAmuseType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    Optional<BusinessAmuseType> findByTypeAndDeleteIsFalseAndAvailableIsTrue(String type);

}
