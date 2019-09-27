package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.learningData.LearnDataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LearnDataTypeRepository extends JpaRepository<LearnDataType, String>, JpaSpecificationExecutor<LearnDataType> {

    Optional<LearnDataType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<LearnDataType> findAllByDeleteIsFalseAndAvailableIsTrue();

    List<LearnDataType> findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();

}
