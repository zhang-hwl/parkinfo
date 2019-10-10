package com.parkinfo.web.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddParkWorkPlanRequest;
import com.parkinfo.request.taskManage.ExportWorkPlanRequest;
import com.parkinfo.request.taskManage.QueryWorkPlanListRequest;
import com.parkinfo.request.taskManage.SetParkWorkPlanRequest;
import com.parkinfo.response.taskManage.ParkWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.ParkWorkPlanListResponse;
import com.parkinfo.service.taskManage.IParkWorkPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 17:41
 **/
@RestController
@RequestMapping("/taskManage/parkWorkPlan")
@Api(value = "/taskManage/parkWorkPlan", tags = {"任务管理-园区工作计划及小节"})
public class ParkWorkPlanController {

    @Autowired
    private IParkWorkPlanService parkWorkPlanService;

    @PostMapping("/search")
    @ApiOperation(value = "查询园区工作计划及小节")
    @RequiresPermissions("taskManage:parkWorkPlan:parkWorkPlan_search")
    public Result<Page<ParkWorkPlanListResponse>> search(@Valid @RequestBody QueryWorkPlanListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ParkWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return parkWorkPlanService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查询园区工作计划及小节详情")
    @RequiresPermissions("taskManage:parkWorkPlan:parkWorkPlan_detail")
    public Result<ParkWorkPlanDetailResponse> detail(@PathVariable("id") String id){
        return parkWorkPlanService.detail(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "创建园区工作计划及小节")
    @RequiresPermissions("taskManage:parkWorkPlan:parkWorkPlan_add")
    public  Result addTask(@Valid @RequestBody AddParkWorkPlanRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ParkWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return parkWorkPlanService.addTask(request);
    }

    @PostMapping("/set")
    @ApiOperation(value = "编辑及提交任务")
    @RequiresPermissions("taskManage:parkWorkPlan:parkWorkPlan_set")
    public Result setTask(@Valid @RequestBody SetParkWorkPlanRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ParkWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return parkWorkPlanService.setTask(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除任务")
    @RequiresPermissions("taskManage:parkWorkPlan:parkWorkPlan_delete")
    public  Result deleteTask(@PathVariable("id") String id){
        return parkWorkPlanService.deleteTask(id);
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出园区工作计划及小节详情")
    public void exportWorkPlan(@RequestBody ExportWorkPlanRequest request, HttpServletResponse response){
        parkWorkPlanService.exportWorkPlan(request,response);
    }
}
