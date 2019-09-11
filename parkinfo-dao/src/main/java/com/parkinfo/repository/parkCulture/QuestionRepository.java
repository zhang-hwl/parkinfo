package com.parkinfo.repository.parkCulture;

import com.parkinfo.entity.parkCulture.Question;
import com.parkinfo.enums.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,String> {

    Long countByCategory_IdAndQuestionTypeAndAvailableIsTrueAndDeleteIsFalse(String categoryId, QuestionType questionType);

    @Query(nativeQuery = true,value = "select id from c_examination_question where category_id =?1 and question_type=?2 order by random() limit ?3")
    List<Question> queryIdsByCategory(String categoryId, Integer questionTypeNumber,Integer count);

    Page<Question> findAll(Specification<Question> questionSpecification, Pageable pageable);
}
