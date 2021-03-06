package com.parkinfo.web.homePage;

import com.parkinfo.common.Result;
import com.parkinfo.entity.notice.SystemNotice;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.notice.QueryNoticeRequest;
import com.parkinfo.service.homePage.ISystemNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/homePage/notice")
@Api(value = "SystemNoticeController", tags = {"公告维护"})
public class SystemNoticeController {

    @Autowired
    private ISystemNoticeService systemNoticeService;

    @PostMapping("/add")
    @ApiOperation(value = "新增公告")
    @RequiresPermissions("sysConfig:news:add")
    public Result<String> addNotice(@Valid @RequestBody SystemNotice systemNotice, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return systemNoticeService.addNotice(systemNotice);
    }

    @PostMapping("/findAll")
    @RequiresPermissions("sysConfig:news:search")
    @ApiOperation(value = "查询公告")
    public Result<Page<SystemNotice>> findAll(@RequestBody QueryNoticeRequest request){
        return systemNoticeService.findAll(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除公告")
    @RequiresPermissions("sysConfig:news:delete")
    public Result<String> deleteById(@PathVariable("id") String id){
        return systemNoticeService.deleteById(id);
    }

    @PostMapping("/find/{count}")
    @RequiresPermissions(value = {"sysConfig:news:search", "HRhome:notice:search", "PERSONALhome:notice:search"}, logical = Logical.OR)
    @ApiOperation(value = "返回count条最新公告")
    public Result<List<SystemNotice>> findByLimit(@PathVariable("count") Integer count){
        return systemNoticeService.findByLimit(count);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑公告")
    @RequiresPermissions("sysConfig:news:edit")
    public Result<String> editNotice(@Valid @RequestBody SystemNotice systemNotice, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return systemNoticeService.editNotice(systemNotice);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看公告")
    @RequiresPermissions("sysConfig:news:detail")
    public Result<SystemNotice> detail(@PathVariable("id") String id){
        return systemNoticeService.detail(id);
    }

}
