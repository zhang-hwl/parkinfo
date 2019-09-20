package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.learningData.LearnDataType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LearnDataTypeRepository extends JpaRepository<LearnDataType, String> {

    Optional<LearnDataType> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<LearnDataType> findAllByDeleteIsFalseAndAvailableIsTrue();

    List<LearnDataType> findAllByParentIsNullAndDeleteIsFalseAndAvailableIsTrue();

}
