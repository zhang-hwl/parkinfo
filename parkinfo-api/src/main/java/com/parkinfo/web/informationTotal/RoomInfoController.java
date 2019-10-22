package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.enums.TemplateEnum;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import com.parkinfo.service.informationTotal.IRoomInfoService;
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
@RequestMapping("infoTotal/roomInfo")
@Api(value = "/infoTotal/roomInfo", tags = {"信息统计-本园区房间统计"})
public class RoomInfoController {

    @Autowired
    private IRoomInfoService roomInfoService;
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增本园区房间统计")
    @RequiresPermissions(value = "infoTotal:room:add")
    public Result<String> add(@RequestBody RoomInfoRequest request){
        return roomInfoService.add(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑该房间统计")
    @RequiresPermissions(value = "infoTotal:room:edit")
    public Result<String> edit(@RequestBody RoomInfoRequest request){
        return roomInfoService.edit(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除该房间统计")
    @RequiresPermissions(value = "infoTotal:room:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return roomInfoService.delete(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询本园区房间统计")
    @RequiresPermissions(value = "infoTotal:room:search")
    public Result<Page<RoomInfoRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return roomInfoService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入本园区房间统计")
    @RequiresPermissions(value = "infoTotal:room:add")
    public Result<String> myImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        return roomInfoService.myImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载本园区房间统计模板")
    public Result<String> export(){
        return templateService.getTemplateUrl(TemplateEnum.ROOM.getName());
    }

    @GetMapping("/download/{id}/{parkId}")
    @ApiOperation(value = "文件导出", notes = "{用户ID}/{园区ID}")
    public void download(@PathVariable("id")String id, @PathVariable("parkId") String parkId, HttpServletResponse response){
        roomInfoService.download(id, parkId, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("本园区房间统计").getData();
        return Result.<List<String>>builder().success().data(list).build();
    }

}
