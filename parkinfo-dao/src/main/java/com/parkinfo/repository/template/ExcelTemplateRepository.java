package com.parkinfo.repository.template;

import com.parkinfo.entity.template.ExcelTemplate;
import com.parkinfo.enums.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExcelTemplateRepository extends JpaRepository<ExcelTemplate,String> {

    List<ExcelTemplate> findAllByDeleteIsFalse();

    Optional<ExcelTemplate> findByIdAndDeleteIsFalse(String id);

    Optional<ExcelTemplate> findByTypeAndDeleteIsFalse(TemplateType type);
}
