package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,String> {
}
