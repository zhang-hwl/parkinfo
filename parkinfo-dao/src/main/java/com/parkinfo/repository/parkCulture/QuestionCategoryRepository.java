package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory,String> {
    Page<QuestionCategory> findAll(Specification<QuestionCategory> questionCategorySpecification, Pageable pageable);

    List<QuestionCategory> findAll(Specification<QuestionCategory> queryCategorySpecification);
}
