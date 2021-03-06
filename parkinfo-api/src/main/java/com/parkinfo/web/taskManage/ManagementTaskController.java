package com.parkinfo.web.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddManagementTaskRequest;
import com.parkinfo.request.taskManage.QueryManagementTaskRequest;
import com.parkinfo.request.taskManage.SetTaskExecutedRequest;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserListResponse;
import com.parkinfo.response.taskManage.ManagementTaskDetailResponse;
import com.parkinfo.response.taskManage.ManagementTaskListResponse;
import com.parkinfo.service.parkCulture.ILibraryService;
import com.parkinfo.service.taskManage.IManagementTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-16 13:33
 **/
@RestController
@RequestMapping("/taskManage/managementTask")
@Api(value = "/taskManage/managementTask", tags = {"任务管理-管理制度任务"})
public class ManagementTaskController {

    @Autowired
    private IManagementTaskService managementTaskService;

    @Autowired
    private ILibraryService libraryService;

    @PostMapping("/search")
    @ApiOperation(value = "查询管理制度任务")
    @RequiresPermissions("taskManage:managementTask:managementTask_search")
    public Result<Page<ManagementTaskListResponse>> search(@Valid @RequestBody QueryManagementTaskRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ManagementTaskListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementTaskService.search(request);

    }

    /**
     * 查询管理制度任务详情
     * @param id
     * @return
     */
    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查询管理制度任务详情")
    @RequiresPermissions("taskManage:managementTask:managementTask_detail")
    public Result<ManagementTaskDetailResponse> detail(@PathVariable("id") String id){
        return managementTaskService.detail(id);
    }

    /**
     * 创建管理制度任务
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建管理制度任务")
    @RequiresPermissions("taskManage:managementTask:managementTask_add")
    public Result addTask(@Valid @RequestBody AddManagementTaskRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<ManagementTaskListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementTaskService.addTask(request);
    }

    /**
     * 删除管理制度任务
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除管理制度任务")
    @RequiresPermissions("taskManage:managementTask:managementTask_delete")
    public Result deleteTask(@PathVariable("id") String id){
        return managementTaskService.deleteTask(id);
    }

    @PostMapping("/park/list")
    @ApiOperation(value = "管理员获取园区列表")
    @RequiresPermissions("taskManage:managementTask:managementTask_add")
    public Result<List<ParkInfoListResponse>> getParkList(){
        return libraryService.getParkList();
    }

    @PostMapping("/user/list/{parkId}")
    @ApiOperation(value = "管理员获取某个园区的人员列表")
    @RequiresPermissions("taskManage:managementTask:managementTask_add")
    public Result<List<ParkUserListResponse>> getUserList(@PathVariable("parkId") String parkId){
        return libraryService.getUserList(parkId);
    }

    @PostMapping("/setTaskExecuted")
    @ApiOperation(value = "设置管理制度任务任务完成情况")
    public Result setTaskExecuted(@Valid @RequestBody SetTaskExecutedRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return managementTaskService.setTaskExecuted(request);
    }
    
}
