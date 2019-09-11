package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.request.parkService.meetingRoom.SearchMeetingRoomRequest;
import com.parkinfo.request.parkService.serviceFlow.AddServiceFlowImgRequest;
import com.parkinfo.request.parkService.serviceFlow.SearchServiceFlowImgRequest;
import com.parkinfo.response.parkService.MeetingRoomResponse;
import com.parkinfo.service.parkService.IServiceFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/parkService/serviceFlowImg")
@Api(value = "/parkService/serviceFlow", tags = {"园区服务-园区服务流程查询"})
public class ServiceFlowController {
    @Autowired
    private IServiceFlowService serviceFlowService;

    @PostMapping("/search")
    @ApiOperation(value = "获取园区服务流程图")
    @RequiresPermissions("parkService:serviceFlow:serviceFlow:search")
    public Result<ServiceFlowImg> searchServiceFlowImg(@Valid @RequestBody SearchServiceFlowImgRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<ServiceFlowImg>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return serviceFlowService.searchServiceFlowImg(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "新增/编辑园区服务流程图")
    @RequiresPermissions("parkService:serviceFlow:serviceFlow:edit")
    public Result<String> editServiceFlowImg(@Valid @RequestBody AddServiceFlowImgRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return serviceFlowService.editServiceFlowImg(request);
    }
}
