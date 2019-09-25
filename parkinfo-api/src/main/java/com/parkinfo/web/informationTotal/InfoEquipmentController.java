package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.InfoEquipmentRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.IInfoEquipmentService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/infoEquipment")
@Api(value = "/infoTotal/infoEquipment", tags = {"信息统计-信息化设备"})
public class InfoEquipmentController {

    @Autowired
    private IInfoEquipmentService infoEquipmentService;
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增信息化设备")
    @RequiresPermissions(value = "infoTotal:info:add")
    public Result<String> addPolicyTotal(@RequestBody InfoEquipmentRequest request){
        return infoEquipmentService.add(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑信息化设备")
    @RequiresPermissions(value = "infoTotal:info:edit")
    public Result<String> editPolicyTotal(@RequestBody InfoEquipmentRequest request){
        return infoEquipmentService.edit(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除信息化设备")
    @RequiresPermissions(value = "infoTotal:info:delete")
    public Result<String> deletePolicyTotal(@PathVariable("id") String id){
        return infoEquipmentService.delete(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询信息化设备")
    @RequiresPermissions(value = "infoTotal:info:search")
    public Result<Page<InfoEquipmentRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return infoEquipmentService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入信息化设备")
    @RequiresPermissions(value = "infoTotal:info:add")
    public Result<String> policyTotalImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        return infoEquipmentService.myImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载信息化设备模板")
    public Result<String> export(){
        return templateService.getTemplateUrl("信息化设备");
    }

    @GetMapping("/download/{id}")
    @ApiOperation(value = "文件导出", notes = "传用户id")
    public void download(@PathVariable("id")String id, HttpServletResponse response){
        infoEquipmentService.download(id, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("信息化设备").getData();
        return Result.<List<String>>builder().success().data(list).build();
    }

}
