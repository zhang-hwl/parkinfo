package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.enums.TemplateEnum;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.IBigEventService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/bigEvent")
@Api(value = "/infoTotal/bigEvent", tags = {"信息统计-园区大事件"})
public class BigEventController {

    @Autowired
    private IBigEventService bigEventService;
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增园区大事件")
    @RequiresPermissions(value = "infoTotal:bigEvent:add")
    public Result<String> add(@Valid  @RequestBody BigEventRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return bigEventService.add(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑园区大事件")
    @RequiresPermissions(value = "infoTotal:bigEvent:edit")
    public Result<String> edit(@Valid @RequestBody BigEventRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return bigEventService.edit(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除园区大事件")
    @RequiresPermissions(value = "infoTotal:bigEvent:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return bigEventService.delete(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询园区大事件")
    @RequiresPermissions(value = "infoTotal:bigEvent:search")
    public Result<Page<BigEventRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return bigEventService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入园区大事件")
    @RequiresPermissions(value = "infoTotal:bigEvent:add")
    public Result<String> myImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("parkId") String parkId){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        request.setParkId(parkId);
        return bigEventService.myImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载园区大事件模板")
    public Result<String> export(){
        return templateService.getTemplateUrl(TemplateEnum.BIG_EVENT.getName());
    }

    @GetMapping("/download/{id}/{parkId}")
    @ApiOperation(value = "文件导出", notes = "{用户ID}/{园区ID}")
    public void download(@PathVariable("id")String id, @PathVariable("parkId") String parkId, HttpServletResponse response){
        bigEventService.download(id, parkId, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("园区大事记").getData();
        return Result.<List<String>>builder().success().data(list).build();
    }

}
