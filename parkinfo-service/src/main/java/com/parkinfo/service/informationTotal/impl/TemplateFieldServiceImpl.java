package com.parkinfo.service.informationTotal.impl;

import com.parkinfo.entity.informationTotal.TemplateField;
import com.parkinfo.repository.informationTotal.TemplateFieldRepository;
import com.parkinfo.service.informationTotal.ITemplateFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateFieldServiceImpl implements ITemplateFieldService {

    @Autowired
    private TemplateFieldRepository templateFieldRepository;

    @Override
    public List<TemplateField> findByGeneral(String general) {
       return templateFieldRepository.findAllByGeneralAndDeleteIsFalseAndAvailableIsTrue(general);
    }
}
