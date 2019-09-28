package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.learningData.LearnDataComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LearnDataCommentRepository extends JpaRepository<LearnDataComment, String>, JpaSpecificationExecutor<LearnDataComment> {
}
