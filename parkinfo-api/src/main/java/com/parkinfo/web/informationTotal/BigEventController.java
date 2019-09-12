package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.service.informationTotal.IBigEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/bigEvent")
@Api(value = "/infoTotal/bigEvent", tags = {"信息统计-园区大事件"})
public class BigEventController {

    @Autowired
    private IBigEventService bigEventService;

    @PostMapping("/add")
    @ApiOperation(value = "新增园区大事件")
    @RequiresPermissions(value = "infoTotal:add")
    public Result<String> add(@RequestBody BigEventRequest request){
        return bigEventService.add(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑园区大事件")
    @RequiresPermissions(value = "infoTotal:edit")
    public Result<String> edit(@RequestBody BigEventRequest request){
        return bigEventService.edit(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除园区大事件")
    @RequiresPermissions(value = "infoTotal:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return bigEventService.delete(id);
    }

    @PostMapping("/all")
    @ApiOperation(value = "查询园区大事件")
    public Result<List<BigEventRequest>> findAll(){
        return bigEventService.findAll();
    }

    @PostMapping("/search/{version}")
    @ApiOperation(value = "根据版本查询园区大事件")
    public Result<List<BigEventRequest>> findByVersion(@PathVariable("version") String version){
        return bigEventService.findByVersion(version);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入园区大事件")
    @RequiresPermissions(value = "infoTotal:add")
    public Result<String> myImport(@RequestBody MultipartFile multipartFile){
        return bigEventService.myImport(multipartFile);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载园区大事件模板")
    public Result<String> export(HttpServletResponse response){
        return bigEventService.export(response);
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件导出")
    public void download(HttpServletResponse response, @RequestBody String version){
        bigEventService.download(response, version);
    }

}
