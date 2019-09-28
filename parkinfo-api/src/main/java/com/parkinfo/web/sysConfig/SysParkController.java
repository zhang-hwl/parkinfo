package com.parkinfo.web.sysConfig;

import com.parkinfo.common.Result;
import com.parkinfo.request.sysConfig.AddSysParkRequest;
import com.parkinfo.request.sysConfig.QuerySysParkRequest;
import com.parkinfo.response.login.ParkInfoResponse;
import com.parkinfo.response.sysConfig.SysParkInfoResponse;
import com.parkinfo.service.sysConfig.ISysParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysConfig/sysPark")
@Api(value = "/sysConfig/sysPark", tags = {"系统设置-园区管理"})
public class SysParkController {

    @Autowired
    private ISysParkService sysParkService;

    @PostMapping("/findAll")
    @ApiOperation(value = "获取所有园区")
    public Result<Page<SysParkInfoResponse>> findAll(@RequestBody QuerySysParkRequest request){
        return sysParkService.findAll(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增园区")
    public Result<String> addPark(@RequestBody AddSysParkRequest request){
        return sysParkService.addPark(request);
    }

    @PostMapping("/edit/{id}")
    @ApiOperation(value = "编辑园区")
    public Result<String> editPark(@PathVariable("id") String parkId, @RequestBody AddSysParkRequest request){
        return sysParkService.editPark(parkId, request);
    }

    @PostMapping("/change/{id}")
    @ApiOperation(value = "改变园区状态")
    public Result<String> deletePark(@PathVariable("id") String id){
        return sysParkService.deletePark(id);
    }


}
