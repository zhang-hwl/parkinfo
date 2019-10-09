package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.TemplateField;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.ICheckRecordService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import com.parkinfo.service.informationTotal.ITemplateFieldService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private ITemplateFieldService templateFieldService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增点检记录表")
    @RequiresPermissions(value = "infoTotal:checkRecord:add")
    public Result<String> add(@RequestBody CheckRecordRequest request){
        return checkRecordService.addCheckRecord(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑点检记录表")
    @RequiresPermissions(value = "infoTotal:checkRecord:edit")
    public Result<String> edit(@RequestBody CheckRecordRequest request){
        return checkRecordService.editCheckRecord(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除点检记录表")
    @RequiresPermissions(value = "infoTotal:checkRecord:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return checkRecordService.deleteCheckRecord(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询点检记录表")
    @RequiresPermissions(value = "infoTotal:checkRecord:search")
    public Result<Page<CheckRecordRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return checkRecordService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入点检记录表")
    @RequiresPermissions(value = "infoTotal:checkRecord:add")
    public Result<String> myImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile, @Param("parkId") String parkId){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        request.setParkId(parkId);
        return checkRecordService.checkRecordImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载点检记录表模板")
    public Result<String> export(){
        return templateService.getTemplateUrl("点检记录表");
    }

    @GetMapping("/download/{id}/{parkId}")
    @ApiOperation(value = "文件导出", notes = "{用户ID}/{园区ID}")
    public void download(@PathVariable("id")String id, @PathVariable("parkId")String parkId, HttpServletResponse response){
        checkRecordService.download(id, parkId, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("点检记录表").getData();
        return Result.<List<String>>builder().success().data(list).build();
    }

    @ApiOperation(value = "查询所有模板中的类型")
    @PostMapping("/find/template/type")
    public Result<List<String>> findTemplateType(){
        List<String> list = Lists.newArrayList();
        List<TemplateField> byGeneral = templateFieldService.findByGeneral("点检记录表");
        byGeneral.forEach(temp -> {
            if(StringUtils.isNotBlank(temp.getType())){
                list.add(temp.getType());
            }
        });
        return Result.<List<String>>builder().success().data(list).build();
    }

}
