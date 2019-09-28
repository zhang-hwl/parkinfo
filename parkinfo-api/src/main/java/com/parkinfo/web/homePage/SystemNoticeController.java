package com.parkinfo.web.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.notice.SystemNotice;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.service.homePage.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homePage/notice")
@Api(value = "SystemNoticeController", tags = {"公告维护"})
public class SystemNoticeController {

    @Autowired
    private ISystemNoticeService systemNoticeService;

    @PostMapping("/add")
    @ApiOperation(value = "新增公告")
    public Result<String> addNotice(@RequestBody SystemNotice systemNotice){
        return systemNoticeService.addNotice(systemNotice);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询公告")
    public Result<Page<SystemNotice>> findAll(@RequestBody PageRequest pageRequest){
        return systemNoticeService.findAll(pageRequest);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除公告")
    public Result<String> deleteById(@PathVariable("id") String id){
        return systemNoticeService.deleteById(id);
    }

}
