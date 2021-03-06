package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.response.parkService.CompanyDataResponse;
import com.parkinfo.service.parkService.IServiceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/parkService/serviceInfo")
@Api(value = "/parkService/serviceInfo", tags = {"园区服务-服务信息填报"})
public class ServiceInfoController {
    @Autowired
    private IServiceInfoService serviceInfoService;

    @PostMapping("/detail")
    @ApiOperation(value = "获取服务需求信息详情")
    @RequiresPermissions("parkService:serviceDemandInfo:serviceInfo:detail")
    public Result<CompanyDataResponse> getCompanyDataResponse(){
        return serviceInfoService.getCompanyDataResponse();
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存服务需求信息详情")
    @RequiresPermissions("parkService:serviceDemandInfo:serviceInfo:save")
    public Result<String> addCompanyDataResponse(@Valid @RequestBody CompanyDataResponse companyDataResponse, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return serviceInfoService.addCompanyDataResponse(companyDataResponse);
    }
}
