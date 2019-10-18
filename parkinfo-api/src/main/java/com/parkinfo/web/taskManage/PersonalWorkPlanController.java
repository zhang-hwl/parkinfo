package com.parkinfo.web.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddPersonalWorkPlanRequest;
import com.parkinfo.request.taskManage.ExportWorkPlanRequest;
import com.parkinfo.request.taskManage.QueryPersonalPlanListRequest;
import com.parkinfo.request.taskManage.SetPersonalWorkPlanRequest;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanDetailResponse;
import com.parkinfo.response.taskManage.PersonalWorkPlanListResponse;
import com.parkinfo.service.parkCulture.ILibraryService;
import com.parkinfo.service.taskManage.IPersonalWorkPlanService;
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
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 17:51
 **/
@RestController
@RequestMapping("/taskManage/personalWorkPlan")
@Api(value = "/taskManage/personalWorkPlan", tags = {"任务管理-个人工作计划及小节"})
public class PersonalWorkPlanController {

    @Autowired
    private IPersonalWorkPlanService personalWorkPlanService;

    @Autowired
    private ILibraryService libraryService;

    @PostMapping("/search")
    @ApiOperation(value = "查询个人工作计划及小节")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_search")
    public Result<Page<PersonalWorkPlanListResponse>> search(@Valid @RequestBody QueryPersonalPlanListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<PersonalWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return personalWorkPlanService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查询个人工作计划及小节详情")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_detail")
    public Result<PersonalWorkPlanDetailResponse> detail(@PathVariable("id") String id){
        return personalWorkPlanService.detail(id);
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出个人工作计划及小节详情")
    public void exportWorkPlan(@RequestBody ExportWorkPlanRequest request, HttpServletResponse response){
        personalWorkPlanService.exportWorkPlan(request,response);
    }

    @PostMapping("/add")
    @ApiOperation(value = "创建个人工作计划及小节")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_add")
    public  Result addTask(@Valid @RequestBody AddPersonalWorkPlanRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<PersonalWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return personalWorkPlanService.addTask(request);
    }

    @PostMapping("/set")
    @ApiOperation(value = "编辑及提交任务")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_set")
    public Result setTask(@Valid @RequestBody SetPersonalWorkPlanRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<PersonalWorkPlanListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return personalWorkPlanService.setTask(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除任务")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_delete")
    public  Result deleteTask(@PathVariable("id") String id){
        return personalWorkPlanService.deleteTask(id);
    }

    @PostMapping("/park/list")
    @ApiOperation(value = "获取园区列表")
    @RequiresPermissions("taskManage:personalWorkPlan:personalWorkPlan_search")
    public Result<List<ParkInfoListResponse>> getParkList(){
        return libraryService.getParkList();
    }
}
