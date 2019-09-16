package com.parkinfo.web.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddSpecialTaskRequest;
import com.parkinfo.request.taskManage.QuerySpecialTaskRequest;
import com.parkinfo.response.taskManage.PersonalWorkPlanListResponse;
import com.parkinfo.response.taskManage.SpecialTaskDetailResponse;
import com.parkinfo.response.taskManage.SpecialTaskListResponse;
import com.parkinfo.service.taskManage.ISpecialTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-12 17:54
 **/
@RestController
@RequestMapping("/taskManage/specialTask")
@Api(value = "/taskManage/specialTask", tags = {"任务管理-专项任务"})
public class SpecialTaskController {

    @Autowired
    private ISpecialTaskService specialTaskService;

    @PostMapping("/search")
    @ApiOperation(value = "查询专项任务")
    @RequiresPermissions("taskManage:specialTask:specialTask_search")
    public Result<Page<SpecialTaskListResponse>> search(@Valid @RequestBody QuerySpecialTaskRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<SpecialTaskListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return specialTaskService.search(request);

    }

    /**
     * 查询专项任务详情
     * @param id
     * @return
     */
    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查询专项任务详情")
    @RequiresPermissions("taskManage:specialTask:specialTask_detail")
    public Result<SpecialTaskDetailResponse> detail(@PathVariable("id") String id){
        return specialTaskService.detail(id);
    }

    /**
     * 创建专项任务
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建专项任务")
    @RequiresPermissions("taskManage:specialTask:specialTask_add")
    public Result addTask(@Valid @RequestBody AddSpecialTaskRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<SpecialTaskListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return specialTaskService.addTask(request);
    }

    /**
     * 删除专项任务
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除专项任务")
    @RequiresPermissions("taskManage:specialTask:specialTask_delete")
    public Result deleteTask(@PathVariable("id") String id){
        return specialTaskService.deleteTask(id);
    }
}
