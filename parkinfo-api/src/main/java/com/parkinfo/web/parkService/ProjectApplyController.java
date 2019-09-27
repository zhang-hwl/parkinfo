package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.parkService.meetingRoom.AddMeetingRoomRequest;
import com.parkinfo.request.parkService.projectApply.AddProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.ChangeStatusRequest;
import com.parkinfo.request.parkService.projectApply.EditProjectInfoRequest;
import com.parkinfo.request.parkService.projectApply.SearchProjectInfoRequest;
import com.parkinfo.response.parkService.ProjectApplyRecordResponse;
import com.parkinfo.response.parkService.ProjectApplyRecordTypeResponse;
import com.parkinfo.response.parkService.ProjectInfoResponse;
import com.parkinfo.service.parkService.IProjectApplyService;
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

@RestController
@RequestMapping("/parkService/projectApply")
@Api(value = "/parkService/projectApply", tags = {"园区服务-项目申请"})
public class ProjectApplyController {

    @Autowired
    private IProjectApplyService projectApplyService;

    @PostMapping("/add")
    @ApiOperation(value = "创建项目")
    @RequiresPermissions("parkService:projectApply:projectApply:add")
    public Result<String> addProjectInfo(@RequestBody AddProjectInfoRequest request){
        return projectApplyService.addProjectInfo(request);
    }

    @PostMapping("/search")
    @ApiOperation(value = "分页获取项目")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:search")
    public Result<Page<ProjectInfoResponse>> searchProjectInfo(@RequestBody SearchProjectInfoRequest request){
        return projectApplyService.searchProjectInfo(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑项目")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:edit")
    public Result<String> editProjectInfo(@Valid @RequestBody EditProjectInfoRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return projectApplyService.editProjectInfo(request);
    }

    @PostMapping("/apply/{id}")
    @ApiOperation(value = "申请项目")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:apply")
    public Result<String> applyProject(@PathVariable("id") String id){
        return projectApplyService.applyProject(id);
    }

    @PostMapping("/applyRecord/search/{projectId}")
    @ApiOperation(value = "查看企业申请记录")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:searchApplyRecord")
    public Result<List<ProjectApplyRecordResponse>> searchCompany(@PathVariable("projectId") String projectId){
        return projectApplyService.searchCompany(projectId);
    }

    @PostMapping("/applyRecord/search")
    @ApiOperation(value = "查看我的申报")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:searchMyApplyRecord")
    public Result<List<ProjectApplyRecordResponse>> searchMyApplyRecord(){
        return projectApplyService.searchMyApplyRecord();
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "修改申报状态")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:changeStatus")
    public Result<String> changeStatus(@Valid @RequestBody ChangeStatusRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return projectApplyService.changeStatus(request);
    }

    @PostMapping("/applyRecord/delete/{recordId}")
    @ApiOperation(value = "删除企业申请记录")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:deleteApplyRecord")
    public Result<String> deleteRecord(@PathVariable("recordId") String recordId){
        return projectApplyService.deleteRecord(recordId);
    }

    @PostMapping("/applyRecord/detail/{recordId}")
    @ApiOperation(value = "查看企业申请记录详情")
    @RequiresPermissions("parkService:projectApply:projectApplyManage:deleteApplyRecord")
    public Result<ProjectApplyRecordResponse> detailRecord(@PathVariable("recordId") String recordId){
        return projectApplyService.detailRecord(recordId);
    }

    @PostMapping("/find/type")
    @ApiOperation(value = "获取项目类型")
    public Result<List<ProjectApplyRecordTypeResponse>> findAllType(){
        return projectApplyService.findAllType();
    }

    @PostMapping("/search/type")
    @ApiOperation(value = "分页获取项目类型")
    public Result<Page<ProjectApplyRecordTypeResponse>> findAllTypePage(@RequestBody PageRequest request){
        return projectApplyService.findAllTypePage(request);
    }


    @PostMapping("/add/type")
    @ApiOperation(value = "新增项目类型")
    public Result<String> addType(@RequestBody  ProjectApplyRecordTypeResponse recordTypeResponse){
        return projectApplyService.addType(recordTypeResponse);
    }

    @PostMapping("/delete/type/{id}")
    @ApiOperation(value = "删除项目类型")
    public Result<String> deleteType(@PathVariable("id") String id){
        return projectApplyService.deleteType(id);
    }

    @PostMapping("/edit/type")
    @ApiOperation(value = "编辑项目类型")
    public Result<String> editType(@RequestBody  ProjectApplyRecordTypeResponse recordTypeResponse){
        return projectApplyService.editType(recordTypeResponse);
    }

}
