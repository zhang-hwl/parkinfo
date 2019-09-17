package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.ManageDetailResponse;
import com.parkinfo.response.companyManage.ManagementResponse;
import com.parkinfo.service.companyManage.IManagementService;
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
@RequestMapping("/companyManage/management")
@Api(value = "/companyManage/management", tags = {"供需信息-招商信息管理"})
public class ManagementController {

    @Autowired
    private IManagementService managementService;

    @PostMapping("/investImport")
    @ApiOperation("导入招商信息")
    @RequiresPermissions("companyManage:investment:invest_import")
    public Result investImport(@RequestBody MultipartFile file) {
        return managementService.investImport(file);
    }

    @PostMapping("/investExport")
    @ApiOperation("下载招商信息模板")
    //@RequiresPermissions("companyManage:investment:invest_export")
    public Result investExport(HttpServletResponse response) {
        return managementService.investExport(response);
    }

    @PostMapping("/add")
    @ApiOperation("添加招商信息")
    @RequiresPermissions("companyManage:investment:invest_add")
    public Result add(@Valid @RequestBody AddInvestmentRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementService.add(request);
    }

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有招商信息")
    @RequiresPermissions("companyManage:investment:invest_find")
    public Result<Page<ManagementResponse>> findAll(@RequestBody QueryManagementRequest request) {
        return managementService.findAll(request);
    }

    @PostMapping("/enter/{id}")
    @ApiOperation("设置企业入驻")
    @RequiresPermissions("companyManage:investment:invest_enter")
    public Result enter(@PathVariable("id") String id) {
        return managementService.enter(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除招商信息")
    @RequiresPermissions("companyManage:investment:invest_delete")
    public Result delete(@PathVariable("id") String id) {
        return managementService.delete(id);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("查询招商详细信息")
    @RequiresPermissions("companyManage:investment:invest_query")
    public Result<ManageDetailResponse> query(@PathVariable("id") String id) {
        return managementService.query(id);
    }

    @PostMapping("/set")
    @ApiOperation("修改招商基本信息")
    @RequiresPermissions("companyManage:investment:invest_set")
    public Result set(@Valid @RequestBody SetInvestmentRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementService.set(request);
    }

    @PostMapping("/bind")
    @ApiOperation("绑定人员")
    @RequiresPermissions("companyManage:investment:invest_bind")
    public Result bind(@Valid @RequestBody BindCompanyRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementService.bind(request);
    }
}
