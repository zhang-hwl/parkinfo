package com.parkinfo.service.informationTotal;

import com.parkinfo.entity.informationTotal.TemplateField;

import java.util.List;

public interface ITemplateFieldService {

    List<TemplateField> findByGeneral(String general);

}
