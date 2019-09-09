package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.request.compayManage.SetCompanyRequireRequest;
import com.parkinfo.response.companyManage.CompanyDetailResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import com.parkinfo.service.companyManage.ICompanyDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/companyManage/companyDetail")
@Api(value = "/companyManage/companyDetail", tags = {"供需信息-企业信息需求"})
public class CompanyDetailController {

    @Autowired
    private ICompanyDetailService companyDetailService;

    @PostMapping("/companyImport")
    @ApiOperation("导入企业信息")
    public Result companyImport(@RequestBody MultipartFile file) {
        return companyDetailService.companyImport(file);
    }

    @PostMapping("/companyExport")
    @ApiOperation("下载企业信息模板")
    public Result companyExport(HttpServletResponse response) {
        return companyDetailService.companyExport(response);
    }

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有企业信息")
    public Result<Page<CompanyResponse>> findAll(@RequestBody QueryCompanyRequest request) {
        return companyDetailService.findAll(request);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("查询企业详细信息")
    public Result<CompanyDetailResponse> query(@PathVariable("id") String id) {
        return companyDetailService.query(id);
    }

    @PostMapping("/set")
    @ApiOperation("修改企业基本信息")
    public Result set(@Valid @RequestBody SetCompanyInfoRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyDetailService.set(request);
    }

    @PostMapping("/setRequire")
    @ApiOperation("修改企业需求信息")
    public Result setRequire(@RequestBody SetCompanyRequireRequest request) {
        return companyDetailService.setRequire(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除企业信息")
    public Result delete(@PathVariable("id") String id) {
        return companyDetailService.delete(id);
    }
}
