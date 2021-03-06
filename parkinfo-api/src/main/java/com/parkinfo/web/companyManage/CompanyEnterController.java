package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import com.parkinfo.service.companyManage.ICompanyEnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/companyManage/enter")
@Api(value = "/companyManage/enter", tags = {"供需信息-入驻企业管理"})
public class CompanyEnterController {
    @Autowired
    private ICompanyEnterService companyEnterService;

    @GetMapping("/enterExport")
    @ApiOperation("导出入驻企业信息")
    public Result enterExport(HttpServletResponse response) {
        return companyEnterService.enterExport(response);
    }

    @PostMapping("/findAll")
    @ApiOperation("查询所有入驻后企业")
    @RequiresPermissions("companyManage:companyEnter:enter_findAll")
    public Result<Page<EnterResponse>> findAll(@RequestBody QueryEnterRequest request) {
        return companyEnterService.findAll(request);
    }

    @PostMapping("/setCompany")
    @ApiOperation("修改入驻企业详情")
    @RequiresPermissions("companyManage:companyEnter:enter_setCompany")
    public Result setCompany(@Valid @RequestBody ModifyCompanyRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyEnterService.setCompany(request);
    }

    @PostMapping("/query/{id}")
    @ApiOperation("查询入驻企业详情")
    @RequiresPermissions("companyManage:companyEnter:enter_query")
    public Result<EnterDetailResponse> query(@PathVariable("id") String id) {
        return companyEnterService.query(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除入驻企业")
    @RequiresPermissions("companyManage:companyEnter:enter_delete")
    public Result delete(@PathVariable("id") String id) {
        return companyEnterService.delete(id);
    }
}
