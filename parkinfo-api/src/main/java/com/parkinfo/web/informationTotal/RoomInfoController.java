package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.informationTotal.QueryByVersionRequest;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
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

    @PostMapping("/all")
    @ApiOperation(value = "查询本园区所有房间")
    @RequiresPermissions(value = "infoTotal:room:search")
    public Result<List<RoomInfoRequest>> findAll(){
        return roomInfoService.findAll();
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询本园区房间统计")
    @RequiresPermissions(value = "infoTotal:room:search")
    public Result<List<RoomInfoRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return roomInfoService.findByVersion(request.getVersion());
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入本园区房间统计")
    @RequiresPermissions(value = "infoTotal:room:add")
    public Result<String> myImport(@RequestBody MultipartFile multipartFile){
        return roomInfoService.myImport(multipartFile);
    }

//    @PostMapping("/export")
//    @ApiOperation(value = "下载本园区房间统计模板")
//    public Result<String> export(HttpServletResponse response){
//        return roomInfoService.export(response);
//    }

    @PostMapping("/export")
    @ApiOperation(value = "下载本园区房间统计模板")
    public Result<String> export(){
        return templateService.getTemplateUrl("本园区房间统计");
    }

    @PostMapping("/download")
    @ApiOperation(value = "文件导出")
    @RequiresPermissions(value = "infoTotal:room:export")
    public void download(HttpServletResponse response, @RequestBody QueryByVersionRequest request){
        roomInfoService.download(response, request.getVersion());
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        return null;
    }

}
