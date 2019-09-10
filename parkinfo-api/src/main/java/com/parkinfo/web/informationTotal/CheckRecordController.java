package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import com.parkinfo.service.informationTotal.ICheckRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/checkRecord")
@Api(value = "/infoTotal/checkRecord", tags = {"信息统计-点检记录表"})
public class CheckRecordController {

    @Autowired
    private ICheckRecordService checkRecordService;

    @PostMapping("/add")
    @ApiOperation(value = "新增点检记录表")
    @RequiresPermissions(value = "infoTotal:add")
    public Result<String> add(@RequestBody CheckRecordRequest request){
        return checkRecordService.addCheckRecord(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑点检记录表")
    @RequiresPermissions(value = "infoTotal:edit")
    public Result<String> edit(@RequestBody CheckRecordRequest request){
        return checkRecordService.editCheckRecord(request);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除点检记录表")
    @RequiresPermissions(value = "infoTotal:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return checkRecordService.deleteCheckRecord(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有点检记录表")
    public Result<List<CheckRecordRequest>> findAll(){
        return checkRecordService.findAll();
    }

    @GetMapping("/search/{version}")
    @ApiOperation(value = "根据版本查询点检记录表")
    public Result<List<CheckRecordRequest>> findByVersion(@PathVariable("version") String version){
        return checkRecordService.findByVersion(version);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入点检记录表")
    @RequiresPermissions(value = "infoTotal:add")
    public Result<String> myImport(@RequestBody MultipartFile multipartFile){
        return checkRecordService.checkRecordImport(multipartFile);
    }

    @GetMapping("/export")
    @ApiOperation(value = "下载点检记录表模板")
    public Result<String> export(HttpServletResponse response){
        return checkRecordService.checkRecordExport(response);
    }

}
