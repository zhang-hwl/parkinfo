package com.parkinfo.repository.informationTotal;

import com.parkinfo.entity.informationTotal.TemplateField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateFieldRepository extends JpaRepository<TemplateField, String> {

    List<TemplateField> findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(String general);

}
