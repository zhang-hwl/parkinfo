package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.activityApply.ActivityApply;
import com.parkinfo.request.parkService.activityApply.AddActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.EditActivityApplyRequest;
import com.parkinfo.request.parkService.activityApply.SearchActivityApplyRequest;
import com.parkinfo.response.parkService.ActivityApplyResponse;
import com.parkinfo.service.parkService.IActivityApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/parkService/activityApply")
@Api(value = "/parkService/activityApply", tags = {"园区服务-活动申请管理"})
public class ActivityApplyController {
    @Autowired
    private IActivityApplyService activityApplyService;

    @PostMapping("/search")
    @ApiOperation(value = "分页获取活动申请")
    @RequiresPermissions("parkService:serviceDemandInfo:activityApplyManage:search")
    public Result<Page<ActivityApplyResponse>> searchActivityApply(@RequestBody SearchActivityApplyRequest request){
        return activityApplyService.searchActivityApply(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看活动申请")
    @RequiresPermissions("parkService:serviceDemandInfo:activityApplyManage:detail")
    public Result<ActivityApplyResponse> detail(@PathVariable("id") String id){
        return activityApplyService.detailActivityApply(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增活动申请")
    @RequiresPermissions("parkService:serviceDemandInfo:activityApply:add")
    public Result<String> addActivityApply(@RequestBody AddActivityApplyRequest request){
        return activityApplyService.addActivityApply(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑活动申请")
    @RequiresPermissions("parkService:serviceDemandInfo:activityApplyManage:edit")
    public Result<String> editActivityApply(@Valid @RequestBody EditActivityApplyRequest request , BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return activityApplyService.editActivityApply(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除活动申请")
    @RequiresPermissions("parkService:serviceDemandInfo:activityApplyManage:delete")
    public Result<String> deleteActivityApply(@PathVariable("id") String id){
        return activityApplyService.deleteActivityApply(id);
    }
}
