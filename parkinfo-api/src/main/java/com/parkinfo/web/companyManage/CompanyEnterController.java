package com.parkinfo.web.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.companyManage.EnclosureTotal;
import com.parkinfo.entity.companyManage.EnteredInfo;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import com.parkinfo.service.companyManage.ICompanyEnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/companyManage/enter")
@Api(value = "/companyManage/enter", tags = {"供需信息-入驻企业管理"})
public class CompanyEnterController {
    @Autowired
    private ICompanyEnterService companyEnterService;

    @GetMapping("/enterExport")
    @ApiOperation("导出入驻企业信息")
    //@RequiresPermissions("companyManage:companyEnter:enter_export")
    public Result enterExport(HttpServletResponse response) {
        return companyEnterService.enterExport(response);
    }

    @PostMapping("/findAll")
    @ApiOperation("查询所有入驻后企业")
    @RequiresPermissions("companyManage:companyEnter:enter_findAll")
    public Result<Page<EnterResponse>> findAll(@RequestBody QueryEnterRequest request) {
        return companyEnterService.findAll(request);
    }

    @PostMapping("/modify")
    @ApiOperation("修改分页查询的企业信息")
    @RequiresPermissions("companyManage:companyEnter:enter_modify")
    public Result modify(@RequestBody SetEnterRequest request) {
        return companyEnterService.modify(request);
    }

    @PostMapping("/setCompany")
    @ApiOperation("修改入驻企业详情")
    @RequiresPermissions("companyManage:companyEnter:enter_setCompany")
    public Result setCompany(@RequestBody ModifyCompanyRequest request) {
        return companyEnterService.setCompany(request);
    }

    @PostMapping("/addEnter")
    @ApiOperation("添加入驻信息")
    @RequiresPermissions("companyManage:companyEnter:enter_addEnter")
    public Result addEnter(@RequestBody AddEnterDetailRequest request) {
        return companyEnterService.addEnter(request);
    }

    @PostMapping("/set")
    @ApiOperation("修改入驻信息")
    @RequiresPermissions("companyManage:companyEnter:enter_set")
    public Result set(@RequestBody SetEnterDetailRequest request) {
        return companyEnterService.set(request);
    }

    @PostMapping("/deleteEnter/{id}")
    @ApiOperation("删除入驻信息")
    @RequiresPermissions("companyManage:companyEnter:enter_deleteEnter")
    public Result deleteEnter(@PathVariable("id") String id) {
        return companyEnterService.deleteEnter(id);
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

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件返回文件url")
    @RequiresPermissions("companyManage:companyEnter:enter_ upload")
    public Result<String> uploadFile(HttpServletRequest request) {
        return companyEnterService.uploadFile(request);
    }

    @PostMapping("/addFile")
    @ApiOperation("添加附件")
    @RequiresPermissions("companyManage:companyEnter:enter_addFile")
    public Result addFile(@RequestBody AddFileRequest request) {
        return companyEnterService.addFile(request);
    }
}
