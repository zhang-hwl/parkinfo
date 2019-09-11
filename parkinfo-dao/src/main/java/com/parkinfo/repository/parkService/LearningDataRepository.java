package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.learningData.LearningData;
import com.parkinfo.entity.userConfig.ParkInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LearningDataRepository extends JpaRepository<LearningData,String> {

    Page<LearningData> findAll(Specification<LearningData> specification, Pageable pageable);
}
