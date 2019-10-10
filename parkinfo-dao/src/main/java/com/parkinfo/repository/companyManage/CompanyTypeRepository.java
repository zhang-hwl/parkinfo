package com.parkinfo.repository.companyManage;

import com.parkinfo.entity.companyManage.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author Li
 * description:
 * date: 2019-10-10 11:02
 */
public interface CompanyTypeRepository extends JpaRepository<CompanyType, String>, JpaSpecificationExecutor<CompanyType> {

    List<CompanyType> findAllByDeleteIsFalseAndAvailableIsTrueAndParentIsNull();

    List<CompanyType> findAllByParent_IdAndDeleteIsFalseAndAvailableIsTrue(String id);

    Optional<CompanyType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

}
