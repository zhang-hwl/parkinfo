package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.AnswerSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerSheetRepository extends JpaRepository<AnswerSheet,String> {
    Page<AnswerSheet> findAll(Specification<AnswerSheet> answerSheetSpecification, Pageable pageable);
}
