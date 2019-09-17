package com.parkinfo.repository.parkService;

import com.parkinfo.entity.parkService.feedback.Feedback;
import com.parkinfo.response.parkService.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback,String> {

    Optional<Feedback> findByIdAndDeleteIsFalseAndAvailableIsTrue(String id);

    List<Feedback> findAllByDeleteIsFalseAndAvailableIsTrue();

}
