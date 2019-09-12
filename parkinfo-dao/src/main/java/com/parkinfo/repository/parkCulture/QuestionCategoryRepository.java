package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory,String> {
}
