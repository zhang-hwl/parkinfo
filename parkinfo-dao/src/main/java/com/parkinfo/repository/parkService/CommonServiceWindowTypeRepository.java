package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommonServiceWindowTypeRepository extends JpaRepository<CommonServiceWindowType,String>, JpaSpecificationExecutor<CommonServiceWindowType> {
    Optional<CommonServiceWindowType> findByDeleteIsFalseAndId(String id);

    List<CommonServiceWindowType> findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();

    Optional<CommonServiceWindowType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

}
