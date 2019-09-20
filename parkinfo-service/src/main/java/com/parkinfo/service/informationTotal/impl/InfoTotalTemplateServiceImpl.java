package com.parkinfo.service.informationTotal.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.InfoTotalTemplate;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.InfoTotalTemplateRepository;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InfoTotalTemplateServiceImpl implements IInfoTotalTemplateService {

    @Autowired
    private InfoTotalTemplateRepository infoTotalTemplateRepository;

    @Override
    public Result<String> getTemplateUrl(String type) {
        Optional<InfoTotalTemplate> byType = infoTotalTemplateRepository.findByTypeAndDeleteIsFalseAndAvailableIsTrue(type);
        if(!byType.isPresent()){
            throw new NormalException("模板不存在");
        }
        InfoTotalTemplate infoTotalTemplate = byType.get();
        return Result.<String>builder().success().data(infoTotalTemplate.getTemplateUrl()).build();
    }

}
