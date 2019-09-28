package com.parkinfo.web.taskManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.taskManage.AddGovernmentReportRequest;
import com.parkinfo.request.taskManage.QueryGovernmentReportRequest;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserListResponse;
import com.parkinfo.response.taskManage.GovernmentReportDetailResponse;
import com.parkinfo.response.taskManage.GovernmentReportListResponse;
import com.parkinfo.service.parkCulture.ILibraryService;
import com.parkinfo.service.taskManage.IGovernmentReportService;
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
 * @create 2019-09-16 13:37
 **/
@RestController
@RequestMapping("/taskManage/governmentReport")
@Api(value = "/taskManage/governmentReport", tags = {"任务管理-政府工作汇报任务"})
public class GovernmentReportController {

    @Autowired
    private IGovernmentReportService governmentReportService;

    @Autowired
    private ILibraryService libraryService;

    @PostMapping("/search")
    @ApiOperation(value = "查询政府工作汇报任务")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_search")
    public Result<Page<GovernmentReportListResponse>> search(@Valid @RequestBody QueryGovernmentReportRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<GovernmentReportListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return governmentReportService.search(request);

    }

    /**
     * 查询政府工作汇报任务详情
     * @param id
     * @return
     */
    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查询政府工作汇报任务详情")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_detail")
    public Result<GovernmentReportDetailResponse> detail(@PathVariable("id") String id){
        return governmentReportService.detail(id);
    }

    /**
     * 创建政府工作汇报任务
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建政府工作汇报任务")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_add")
    public Result addTask(@Valid @RequestBody AddGovernmentReportRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<GovernmentReportListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return governmentReportService.addTask(request);
    }

    /**
     * 删除政府工作汇报任务
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除政府工作汇报任务")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_delete")
    public Result deleteTask(@PathVariable("id") String id){
        return governmentReportService.deleteTask(id);
    }


    @PostMapping("/park/list")
    @ApiOperation(value = "管理员获取园区列表")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_add")
    public Result<List<ParkInfoListResponse>> getParkList(){
        return libraryService.getParkList();
    }

    @PostMapping("/user/list/{parkId}")
    @ApiOperation(value = "管理员获取某个园区的人员列表")
    @RequiresPermissions("taskManage:governmentReport:governmentReport_add")
    public Result<List<ParkUserListResponse>> getUserList(@PathVariable("parkId") String parkId){
        return libraryService.getUserList(parkId);
    }
}
