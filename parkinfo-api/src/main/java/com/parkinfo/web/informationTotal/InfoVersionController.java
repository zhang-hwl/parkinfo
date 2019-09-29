package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/infoTotal/version")
@Api(value = "/infoTotal/version", tags = {"信息统计-版本控制"})
public class InfoVersionController {

    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增版本标签")
    @RequiresPermissions("infoTotal:version:add")
    public Result<String> addVersion(@RequestBody InfoVersionResponse response){
        return infoVersionService.add(response);
    }

    @PostMapping("/find/type")
    @ApiOperation(value = "查询所有信息统计类型")
    public Result<List<String>> getGeneral(){
        List<String> list = Lists.newArrayList();
        list.add("政策统计");
        list.add("园区大事记");
        list.add("点检记录表");
        list.add("竞争园区信息");
        list.add("信息化设备");
        list.add("本园区房间统计");
        return Result.<List<String>>builder().success().data(list).build();
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除信息统计版本")
    @RequiresPermissions("infoTotal:version:delete")
    public Result<String> delete(@PathVariable("id") String id){
        return infoVersionService.delete(id);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询信息统计版本")
    @RequiresPermissions("infoTotal:version:search")
    public Result<Page<InfoVersionResponse>> findAll(@RequestBody InfoVersionRequest request){
        return infoVersionService.findAll(request);
    }

}
