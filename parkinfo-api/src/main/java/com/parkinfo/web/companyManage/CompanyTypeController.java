package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.AddGeneralRequest;
import com.parkinfo.request.compayManage.AddKindTypeRequest;
import com.parkinfo.request.compayManage.EditTypeRequest;
import com.parkinfo.request.compayManage.SearchCompanyTypeRequest;
import com.parkinfo.response.companyManage.CompanyTypeDetailResponse;
import com.parkinfo.response.companyManage.CompanyTypeResponse;
import com.parkinfo.service.companyManage.ICompanyTypeService;
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

/**
 * @author Li
 * description:
 * date: 2019-10-10 10:44
 */
@RestController
@RequestMapping("/company")
@Api(value = "/company", tags = "供需信息分类")
public class CompanyTypeController {

    @Autowired
    private ICompanyTypeService companyTypeService;

    @PostMapping("/search")
    @ApiOperation(value = "分页查询分类")
    @RequiresPermissions("company:type:search")
    public Result<Page<CompanyTypeResponse>> search(@RequestBody SearchCompanyTypeRequest request){
        return companyTypeService.search(request);
    }

    @PostMapping("/find")
    @ApiOperation(value = "查询分类")
    public Result<List<CompanyTypeResponse>> findAll(){
        return companyTypeService.findAll();
    }

    @PostMapping("/find/kind/{id}")
    @ApiOperation(value = "获取大类下的所有小类，路径加大类id")
    public Result<List<CompanyTypeDetailResponse>> findAllKind(@PathVariable("id") String id){
        return companyTypeService.findAllKind(id);
    }

    @PostMapping("/find/general")
    @ApiOperation(value = "获取所有大类(不带小类)")
    public Result<List<CompanyTypeDetailResponse>> findAllGeneral(){
        return companyTypeService.findAllGeneral();
    }

    @PostMapping("/add/general")
    @ApiOperation(value = "新增大类")
    public Result<String> addGeneral(@Valid @RequestBody AddGeneralRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyTypeService.addGeneral(request);
    }

    @PostMapping("/add/kind")
    @ApiOperation(value = "新增小类")
    public Result<String> addKind(@Valid @RequestBody AddKindTypeRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyTypeService.addKind(request);
    }

    @PostMapping("/edit/kind")
    @ApiOperation(value = "编辑类型，传入修改的类型名称")
    public Result<String> editGeneral(@Valid @RequestBody EditTypeRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return companyTypeService.editGeneral(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除类型")
    public Result<String> deleteGeneral(@PathVariable("id") String id){
        return companyTypeService.deleteGeneral(id);
    }

}
