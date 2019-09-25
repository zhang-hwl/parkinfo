package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/infoTotal/version")
@Api(value = "/infoTotal/version", tags = {"信息统计-版本控制"})
public class InfoVersionController {

    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
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

}
