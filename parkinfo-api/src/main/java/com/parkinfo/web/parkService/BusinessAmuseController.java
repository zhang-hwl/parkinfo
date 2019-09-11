package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.businessAmuse.EditBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.SearchBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.AddBusinessAmuseRequest;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import com.parkinfo.service.parkService.IBusinessAmuseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/parkService/businessAmuse")
@Api(value = "/parkService/businessAmuse", tags = {"园区服务-商务&周边娱乐"})
public class BusinessAmuseController {
    @Autowired
    private IBusinessAmuseService businessAmuseService;

    @PostMapping("/search")
    @ApiOperation(value = "分页获取商务&周边娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:search")
    public Result<Page<BusinessAmuseResponse>> searchBusinessAmuse(@RequestBody SearchBusinessAmuseRequest request){
        return businessAmuseService.searchBusinessAmuse(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增商务&周边娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:add")
    public Result<String> addBusinessAmuse(@RequestBody AddBusinessAmuseRequest request){
        return businessAmuseService.addBusinessAmuse(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑商务&周边娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:edit")
    public Result<String> editBusinessAmuse(@Valid @RequestBody EditBusinessAmuseRequest request , BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return businessAmuseService.editBusinessAmuse(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除商务&周边娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:delete")
    public Result<String> deleteBusinessAmuse(@PathVariable("id") String id){
        return businessAmuseService.deleteBusinessAmuse(id);
    }
}
