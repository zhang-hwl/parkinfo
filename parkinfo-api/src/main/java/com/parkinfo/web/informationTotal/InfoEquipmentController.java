package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.InfoEquipmentRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.service.informationTotal.IInfoEquipmentService;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add")
    @ApiOperation(value = "新增信息化设备")
    @RequiresPermissions(value = "infoTotal:add")
    public Result<String> addPolicyTotal(@RequestBody InfoEquipmentRequest request){
        return infoEquipmentService.add(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑信息化设备")
    @RequiresPermissions(value = "infoTotal:edit")
    public Result<String> editPolicyTotal(@RequestBody InfoEquipmentRequest request){
        return infoEquipmentService.edit(request);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除信息化设备")
    @RequiresPermissions(value = "infoTotal:delete")
    public Result<String> deletePolicyTotal(@PathVariable("id") String id){
        return infoEquipmentService.delete(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有信息化设备")
    public Result<List<InfoEquipmentRequest>> findAll(){
        return infoEquipmentService.findAll();
    }

    @GetMapping("/search/{version}")
    @ApiOperation(value = "根据版本查询信息化设备")
    public Result<List<InfoEquipmentRequest>> findByVersion(@PathVariable("version") String version){
        return infoEquipmentService.findByVersion(version);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入信息化设备")
    public Result<String> policyTotalImport(@RequestBody MultipartFile multipartFile){
        return infoEquipmentService.myImport(multipartFile);
    }

    @GetMapping("/export")
    @ApiOperation(value = "下载信息化设备模板")
    public Result<String> policyTotalExport(HttpServletResponse response){
        return infoEquipmentService.export(response);
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件导出")
    public void download(HttpServletResponse response, @RequestBody String version){
        infoEquipmentService.download(response, version);
    }

}
