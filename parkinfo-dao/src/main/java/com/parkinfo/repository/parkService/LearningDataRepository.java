package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.learningData.LearningData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningDataRepository extends JpaRepository<LearningData,String> {
}
