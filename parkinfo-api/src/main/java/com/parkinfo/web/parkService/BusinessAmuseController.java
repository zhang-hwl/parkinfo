package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.parkService.businessAmuse.*;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import com.parkinfo.response.parkService.BusinessAmuseTypeResponse;
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
import java.util.List;

@RestController
@RequestMapping("/parkService/businessAmuse")
@Api(value = "/parkService/businessAmuse", tags = {"园区服务-服务商&商务娱乐"})
public class BusinessAmuseController {
    @Autowired
    private IBusinessAmuseService businessAmuseService;

    @PostMapping("/search/serve")
    @ApiOperation(value = "分页获取服务商")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:search")
    public Result<Page<BusinessAmuseResponse>> searchServeBusinessAmuse(@RequestBody SearchBusinessAmuseRequest request){
        String bigTypeId = businessAmuseService.findGeneralId("周边服务商");
        request.setBigTypeId(bigTypeId);
        return businessAmuseService.searchBusinessAmuse(request);
    }

    @PostMapping("/search/happy")
    @ApiOperation(value = "分页获取商务娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:happy:search")
    public Result<Page<BusinessAmuseResponse>> searchHappyBusinessAmuse(@RequestBody SearchBusinessAmuseRequest request){
        String bigTypeId = businessAmuseService.findGeneralId("周边配套娱乐");
        request.setBigTypeId(bigTypeId);
        return businessAmuseService.searchBusinessAmuse(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增服务商")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:add")
    public Result<String> addBusinessAmuse2(@RequestBody AddBusinessAmuseRequest request){
        return businessAmuseService.addBusinessAmuse(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑服务商")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:edit")
    public Result<String> editBusinessAmuse2(@Valid @RequestBody EditBusinessAmuseRequest request , BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return businessAmuseService.editBusinessAmuse(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除服务商")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:delete")
    public Result<String> deleteBusinessAmuse2(@PathVariable("id") String id){
        return businessAmuseService.deleteBusinessAmuse(id);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看服务商&商务娱乐")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:detail")
    public Result<BusinessAmuseResponse> searchBusinessAmuse2(@PathVariable String id){
        return businessAmuseService.detailBusinessAmuse(id);
    }

    @PostMapping("/add/happy")
    @ApiOperation(value = "新增商务娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:happy:add")
    public Result<String> addBusinessAmuse(@RequestBody AddBusinessAmuseRequest request){
        return businessAmuseService.addBusinessAmuse(request);
    }

    @PostMapping("/edit/happy")
    @ApiOperation(value = "编辑商务娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:happy:edit")
    public Result<String> editBusinessAmuse(@Valid @RequestBody EditBusinessAmuseRequest request , BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return businessAmuseService.editBusinessAmuse(request);
    }

    @PostMapping("/delete/happy/{id}")
    @ApiOperation(value = "删除商务娱乐")
    @RequiresPermissions("parkService:serviceFlow:businessAmuse:happy:delete")
    public Result<String> deleteBusinessAmuse(@PathVariable("id") String id){
        return businessAmuseService.deleteBusinessAmuse(id);
    }

    @PostMapping("/type/search/serve")
    @ApiOperation(value = "查看周边服务商分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:search")
    public Result<List<BusinessAmuseTypeResponse>> searchBusinessAmuseServeType(){
        return businessAmuseService.findAllBusinessAmuseTypeByServe();
    }

    @PostMapping("/type/search/serve/page")
    @ApiOperation(value = "分页查看周边服务商分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:search")
    public Result<Page<BusinessAmuseTypeResponse>> searchBusinessAmuseServeTypePage(@RequestBody PageRequest request){
        return businessAmuseService.findAllBusinessAmuseTypeByServePage(request);
    }

    @PostMapping("/type/search/happy")
    @ApiOperation(value = "查看周边配套娱乐分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:search")
    public Result<List<BusinessAmuseTypeResponse>> searchBusinessAmuseHappyType(){
        return businessAmuseService.findAllBusinessAmuseTypeByHappy();
    }

    @PostMapping("/type/search/happy/page")
    @ApiOperation(value = "分页查看周边配套娱乐分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:search")
    public Result<Page<BusinessAmuseTypeResponse>> searchBusinessAmuseHappyTypePage(@RequestBody PageRequest request){
        return businessAmuseService.findAllBusinessAmuseTypeByHappyPage(request);
    }

    @PostMapping("/type/add/serve")
    @ApiOperation(value = "新增园区服务分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:add")
    public Result<String> addBusinessAmuseServeType(@RequestBody AddBusinessAmuseTypeRequest request){
        return businessAmuseService.addBusinessAmuseServeType(request);
    }

    @PostMapping("/type/add/happy")
    @ApiOperation(value = "新增园区配套娱乐分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:add")
    public Result<String> addBusinessAmuseHappyType(@RequestBody AddBusinessAmuseTypeRequest request){
        return businessAmuseService.addBusinessAmuseHappyType(request);
    }

    @PostMapping("/type/edit")
    @ApiOperation(value = "编辑分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:edit")
    public Result<String> editBusinessAmuseType(@RequestBody EditBusinessAmuseTypeRequest request){
        return businessAmuseService.editBusinessAmuseType(request);
    }

    @PostMapping("/type/delete/{id}")
    @ApiOperation(value = "删除分类")
//    @RequiresPermissions("parkService:serviceFlow:businessAmuse:type:delete")
    public Result<String> deleteBusinessAmuseType(@PathVariable("id") String id){
        return businessAmuseService.deleteBusinessAmuseType(id);
    }
}
