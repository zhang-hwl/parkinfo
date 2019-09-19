package com.parkinfo.web.template;

import com.parkinfo.common.Result;
import com.parkinfo.entity.template.ExcelTemplate;
import com.parkinfo.request.template.AddExcelTemplateRequest;
import com.parkinfo.service.template.IExcelTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/excelTemplate/excel")
@Api(value = "/excelTemplate/excel", tags = {"模板管理"})
public class ExcelTemplateController {

    @Autowired
    private IExcelTemplateService excelTemplateService;

    @PostMapping("/upload")
    @ApiOperation("上传模板")
    public Result<String> upload(HttpServletRequest request) {
       return excelTemplateService.upload(request);
    }

    @PostMapping("/add")
    @ApiOperation("模板添加到数据库")
    public Result add(@RequestBody AddExcelTemplateRequest request) {
        return excelTemplateService.add(request);
    }

    @PostMapping("/find")
    @ApiOperation("获取所有模板")
    public Result<List<ExcelTemplate>> find() {
        return excelTemplateService.find();
    }

    @PostMapping("/down/{id}")
    @ApiOperation("获取模板")
    public Result<ExcelTemplate> down(@PathVariable String id) {
        return excelTemplateService.down(id);
    }
}
