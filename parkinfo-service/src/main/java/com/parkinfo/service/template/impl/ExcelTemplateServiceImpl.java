package com.parkinfo.service.template.impl;

import com.parkinfo.common.Result;
import com.parkinfo.entity.template.ExcelTemplate;
import com.parkinfo.enums.FileUploadType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.template.ExcelTemplateRepository;
import com.parkinfo.request.template.AddExcelTemplateRequest;
import com.parkinfo.service.template.IExcelTemplateService;
import com.parkinfo.tools.oss.IOssService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelTemplateServiceImpl implements IExcelTemplateService {

    @Autowired
    private ExcelTemplateRepository excelTemplateRepository;

    @Autowired
    private IOssService ossService;

    @Override
    public Result<String> upload(HttpServletRequest request) {
        String url = ossService.MultipartFileUpload(request, FileUploadType.TEMPLATE.toString());
        return Result.<String>builder().success().data(url).build();
    }

    @Override
    public Result add(AddExcelTemplateRequest request) {
        ExcelTemplate excelTemplate = new ExcelTemplate();
        BeanUtils.copyProperties(request,excelTemplate);
        excelTemplateRepository.save(excelTemplate);
        return Result.builder().success().message("上传成功").build();
    }

    @Override
    public Result<List<ExcelTemplate>> find() {
        List<ExcelTemplate> templateList = excelTemplateRepository.findAllByDeleteIsFalse();
        return Result.<List<ExcelTemplate>>builder().success().data(templateList).build();
    }

    @Override
    public Result<ExcelTemplate> down(String id) {
        Optional<ExcelTemplate> optionalExcelTemplate = excelTemplateRepository.findByIdAndDeleteIsFalse(id);
        if (!optionalExcelTemplate.isPresent()) {
            throw new NormalException("模板不存在");
        }
        ExcelTemplate excelTemplate = optionalExcelTemplate.get();
        return Result.<ExcelTemplate>builder().success().data(excelTemplate).build();
    }
}
