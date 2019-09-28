package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.AddCompanyInfoRequest;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.response.companyManage.CompanyDemandResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import com.parkinfo.service.companyManage.ICompanyDemandService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.ITemplateFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
public class CompanyDemandController {

    @Autowired
    private ICompanyDemandService companyDemandService;
    @Autowired
    private IInfoTotalTemplateService templateService;

    @PostMapping("/companyImport")
    @ApiOperation("导入需求信息")
    @RequiresPermissions("companyManage:companyInfo:info_import")
    public Result companyImport(@RequestBody MultipartFile file) {
        return companyDemandService.companyImport(file);
    }

    @GetMapping("/companyExport")
    @ApiOperation("下载需求信息模板")
    public Result<String> companyExport(HttpServletResponse response) {
        return templateService.getTemplateUrl("需求信息");
    }

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有需求信息")
    @RequiresPermissions("companyManage:companyInfo:info_find")
    public Result<Page<CompanyResponse>> findAll(@RequestBody QueryCompanyRequest request) {
        return companyDemandService.findAll(request);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("查询需求详细信息")
    @RequiresPermissions("companyManage:companyInfo:info_query")
    public Result<CompanyDemandResponse> query(@PathVariable("id") String id) {
        return companyDemandService.query(id);
    }

    @PostMapping("/add")
    @ApiOperation("添加需求信息")
    @RequiresPermissions("companyManage:companyInfo:info_add")
    public Result add(@Valid @RequestBody AddCompanyInfoRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyDemandService.add(request);
    }

    @PostMapping("/set")
    @ApiOperation("修改需求信息")
    @RequiresPermissions("companyManage:companyInfo:info_set")
    public Result set(@Valid @RequestBody SetCompanyInfoRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyDemandService.set(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除需求信息")
    @RequiresPermissions("companyManage:companyInfo:info_delete")
    public Result delete(@PathVariable("id") String id) {
        return companyDemandService.delete(id);
    }
}
