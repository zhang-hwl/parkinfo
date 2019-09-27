package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.parkService.commonServiceWindow.AddCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.CommonServiceWindowTypeRequest;
import com.parkinfo.request.parkService.commonServiceWindow.EditCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.SearchCommonServiceWindowRequest;
import com.parkinfo.response.parkService.CommonServiceWindowResponse;
import com.parkinfo.response.parkService.CommonServiceWindowResponse;
import com.parkinfo.response.parkService.CommonServiceWindowTypeResponse;
import com.parkinfo.service.parkService.ICommonServiceWindowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
@RequestMapping("/parkService/commonServiceWindow")
@Api(value = "/parkService/commonServiceWindow", tags = {"园区服务-公共服务窗口"})
public class CommonServiceWindowController {
    @Autowired
    private ICommonServiceWindowService commonServiceWindowService;

    @PostMapping("/search")
    @ApiOperation(value = "分页获取公共服务窗口")
    @RequiresPermissions("parkService:serviceFlow:commonServiceWindow:search")
    public Result<Page<CommonServiceWindowResponse>> searchCommonServiceWindow(@RequestBody SearchCommonServiceWindowRequest request){
        return commonServiceWindowService.searchCommonServiceWindow(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看公共服务窗口详情")
    @RequiresPermissions("parkService:serviceFlow:commonServiceWindow:detail")
    public Result<CommonServiceWindowResponse> detail(@PathVariable("id") String id){
        return commonServiceWindowService.detail(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增公共服务窗口")
    @RequiresPermissions("parkService:serviceFlow:commonServiceWindow:add")
    public Result<String> addCommonServiceWindow(@RequestBody AddCommonServiceWindowRequest request){
        return commonServiceWindowService.addCommonServiceWindow(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑公共服务窗口")
    @RequiresPermissions("parkService:serviceFlow:commonServiceWindow:edit")
    public Result<String> editCommonServiceWindow(@Valid @RequestBody EditCommonServiceWindowRequest request , BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return commonServiceWindowService.editCommonServiceWindow(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除公共服务窗口")
    @RequiresPermissions("parkService:serviceFlow:commonServiceWindow:delete")
    public Result<String> deleteCommonServiceWindow(@PathVariable("id") String id){
        return commonServiceWindowService.deleteCommonServiceWindow(id);
    }

    @PostMapping("/find/type")
    @ApiOperation(value = "查看公共服务窗口类型")
    public Result<List<CommonServiceWindowTypeResponse>> findAllType(){
        return commonServiceWindowService.findAllType();
    }

    @PostMapping("/search/type")
    @ApiOperation(value = "分页查看公共服务窗口类型")
    public Result<Page<CommonServiceWindowTypeResponse>> findAllTypePage(@RequestBody PageRequest request){
        return commonServiceWindowService.findAllTypePage(request);
    }

    @PostMapping("/edit/type")
    @ApiOperation(value = "编辑公共服务窗口类型", notes = "小类名称为空时,新增大类")
    public Result<String> editType(@RequestBody CommonServiceWindowTypeRequest request){
        return commonServiceWindowService.editType(request);
    }

    @PostMapping("/add/type")
    @ApiOperation(value = "新增公共服务窗口类型", notes = "小类名称为空时,新增大类")
    public Result<String> addType(@RequestBody CommonServiceWindowTypeRequest request){
        return commonServiceWindowService.addType(request);
    }

    @PostMapping("/delete/type/{id}")
    @ApiOperation(value = "删除公共服务窗口类型")
    public Result<String> deleteType(@PathVariable("id") String id){
        return commonServiceWindowService.deleteType(id);
    }

}
