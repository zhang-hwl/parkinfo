package com.parkinfo.web.template;

import com.parkinfo.common.Result;
import com.parkinfo.enums.TemplateEnum;
import com.parkinfo.request.template.ExcelTemplateRequest;
import com.parkinfo.response.template.ExcelTemplateTypeResponse;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.template.IExcelTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excelTemplate/excel")
@Api(value = "/excelTemplate/excel", tags = {"模板管理"})
public class ExcelTemplateController {

    @Autowired
    private IExcelTemplateService excelTemplateService;
    @Autowired
    private IInfoTotalTemplateService templateService;

    @PostMapping("/upload")
    @ApiOperation("上传模板")
    public Result<String> upload(@RequestBody ExcelTemplateRequest request) {
        return excelTemplateService.upload(request);
    }

    @PostMapping("/upload/test")
    @ApiOperation("上传模板Swagger测试接口")
    public Result<String> upload(@RequestParam("typeId") String typeId, MultipartFile file) {
        return excelTemplateService.uploadTest(typeId, file);
    }

    @PostMapping("/find/type")
    @ApiOperation("获取所有模板类型")
    public Result<List<ExcelTemplateTypeResponse>> getTemplateType(){
        return excelTemplateService.findAllType();
    }

    @PostMapping("/export/work")
    @ApiOperation(value = "下载工作计划模板")
    public Result<String> exportWork(){
        return templateService.getTemplateUrl(TemplateEnum.WORK_PLAN.getName());
    }

    @PostMapping("/export/exam")
    @ApiOperation(value = "下载考试模板")
    public Result<String> exportExam(){
        return templateService.getTemplateUrl(TemplateEnum.ENTER_BASIC.getName());
    }

}
