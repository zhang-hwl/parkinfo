package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.QueryManagementRequest;
import com.parkinfo.request.compayManage.SetInvestmentRequest;
import com.parkinfo.response.companyManage.ManagementResponse;
import com.parkinfo.service.companyManage.IManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/companyManage/management")
@Api(value = "/companyManage/management", tags = {"供需信息-招商信息管理"})
public class ManagementController {

    @Autowired
    private IManagementService managementService;

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有招商信息")
    public Result<Page<ManagementResponse>> findAll(@RequestBody QueryManagementRequest request) {
        return managementService.findAll(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除招商信息")
    public Result delete(@PathVariable("id") String id) {
        return managementService.delete(id);
    }

    @PostMapping("/set")
    @ApiOperation("修改招商基本信息")
    public Result set(@Valid @RequestBody SetInvestmentRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementService.set(request);
    }
}
