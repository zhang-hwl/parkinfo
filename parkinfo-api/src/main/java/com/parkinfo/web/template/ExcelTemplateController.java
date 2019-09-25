package com.parkinfo.web.template;

import com.parkinfo.common.Result;
import com.parkinfo.request.template.ExcelTemplateRequest;
import com.parkinfo.response.template.ExcelTemplateTypeResponse;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.template.IExcelTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/excelTemplate/excel")
@Api(value = "/excelTemplate/excel", tags = {"模板管理"})
public class ExcelTemplateController {

    @Autowired
    private IExcelTemplateService excelTemplateService;

    @PostMapping("/upload")
    @ApiOperation("上传模板")
    public Result<String> upload(@RequestBody ExcelTemplateRequest request) {
        return excelTemplateService.upload(request);
    }

    @PostMapping("/find/type")
    @ApiOperation("获取所有模板类型")
    public Result<List<ExcelTemplateTypeResponse>> getTemplateType(){
        return excelTemplateService.findAllType();
    }

}
