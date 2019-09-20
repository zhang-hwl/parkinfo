package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.informationTotal.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import com.parkinfo.service.informationTotal.ICompeteGradenInfoService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/competeGradenInfo")
@Api(value = "/infoTotal/competeGradenInfo", tags = {"信息统计-竞争园区信息"})
public class CompeteGradenInfoController {

    @Autowired
    private ICompeteGradenInfoService competeGradenInfoService;
    @Autowired
    private IInfoTotalTemplateService templateService;

    @PostMapping("/add")
    @ApiOperation(value = "新增竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:add")
    public Result<String> addCompeteGradenInfo(@RequestBody CompeteGradenInfoRequest request){
        return competeGradenInfoService.addCompeteGradenInfo(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:edit")
    public Result<String> editCompeteGradenInfo(@RequestBody CompeteGradenInfoRequest request){
        return competeGradenInfoService.editCompeteGradenInfo(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:delete")
    public Result<String> deleteCompeteGradenInfo(@PathVariable("id") String id){
        return competeGradenInfoService.deleteCompeteGradenInfo(id);
    }

    @PostMapping("/all")
    @ApiOperation(value = "查询所有竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:search")
    public Result<List<CompeteGradenInfoRequest>> findAll(){
        return competeGradenInfoService.findAll();
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:search")
    public Result<List<CompeteGradenInfoRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return competeGradenInfoService.findByVersion(request.getVersion());
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:add")
    public Result<String> policyTotalImport(@RequestBody MultipartFile multipartFile){
        return competeGradenInfoService.competeGradenInfoImport(multipartFile);
    }

//    @PostMapping("/export")
//    @ApiOperation(value = "下载竞争园区信息模板")
//    public Result<String> policyTotalExport(HttpServletResponse response){
//        return competeGradenInfoService.competeGradenInfoExport(response);
//    }

    @PostMapping("/export")
    @ApiOperation(value = "下载竞争园区信息模板")
    public Result<String> export(){
        return templateService.getTemplateUrl("竞争园区信息");
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件导出")
    @RequiresPermissions(value = "infoTotal:compete:export")
    public void download(HttpServletResponse response, @RequestBody QueryByVersionRequest request){
        competeGradenInfoService.download(response, request.getVersion());
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        return null;
    }

}
