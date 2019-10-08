package com.parkinfo.service.template.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.InfoTotalTemplate;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.archiveInfo.InfoTotalTemplateRepository;
import com.parkinfo.request.template.ExcelTemplateRequest;
import com.parkinfo.response.template.ExcelTemplateTypeResponse;
import com.parkinfo.service.template.IExcelTemplateService;
import com.parkinfo.service.upload.IUploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ExcelTemplateServiceImpl implements IExcelTemplateService {

    @Autowired
    private InfoTotalTemplateRepository infoTotalTemplateRepository;
    @Autowired
    private IUploadService uploadService;

    @Override
    public Result<String> upload(ExcelTemplateRequest request) {
        Optional<InfoTotalTemplate> byId = infoTotalTemplateRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(request.getTypeId());
        if(!byId.isPresent()){
            throw new NormalException("类型不存在");
        }
        InfoTotalTemplate infoTotalTemplate = byId.get();
        infoTotalTemplate.setTemplateUrl(request.getUrl());
        infoTotalTemplate.setDelete(false);
        infoTotalTemplate.setAvailable(true);
        infoTotalTemplateRepository.save(infoTotalTemplate);
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<String> uploadTest(String typeId, MultipartFile file) {
        Optional<InfoTotalTemplate> byId = infoTotalTemplateRepository.findByIdAndDeleteIsFalseAndAvailableIsTrue(typeId);
        if(!byId.isPresent()){
            throw new NormalException("类型不存在");
        }
        InfoTotalTemplate infoTotalTemplate = byId.get();
        String data = uploadService.fileTest(file).getData();
        infoTotalTemplate.setTemplateUrl(data);
        infoTotalTemplate.setDelete(false);
        infoTotalTemplate.setAvailable(true);
        infoTotalTemplateRepository.save(infoTotalTemplate);
        return Result.<String>builder().success().data("上传成功").build();
    }

    @Override
    public Result<List<ExcelTemplateTypeResponse>> findAllType() {
        List<InfoTotalTemplate> all = infoTotalTemplateRepository.findAllByDeleteIsFalseAndAvailableIsTrue();
        List<ExcelTemplateTypeResponse> result = Lists.newArrayList();
        all.forEach(temp -> {
            ExcelTemplateTypeResponse response = new ExcelTemplateTypeResponse();
            BeanUtils.copyProperties(temp, response);
            result.add(response);
        });
        return Result.<List<ExcelTemplateTypeResponse>>builder().success().data(result).build();
    }


}
