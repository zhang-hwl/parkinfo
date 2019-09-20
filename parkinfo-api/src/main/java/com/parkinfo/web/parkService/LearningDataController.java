package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.request.parkService.learningData.SearchLearningDateRequest;
import com.parkinfo.service.parkService.ILearningDataService;
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
@RequestMapping("/parkService/learningData")
@Api(value = "/parkService/learningData", tags = {"园区服务-学习资料查询"})
public class LearningDataController {
    @Autowired
    private ILearningDataService learningDataService;

    @PostMapping("/search")
    @ApiOperation(value = "分页获取学习资料")
    @RequiresPermissions("parkService:serviceFlow:learningData:search")
    public Result<Page<LearningDateResponse>> searchLearningData(@RequestBody SearchLearningDateRequest request){
        return learningDataService.searchLearningData(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增学习资料")
    @RequiresPermissions("parkService:serviceFlow:learningData:add")
    public Result<String> addLearningData(@RequestBody AddLearningDataRequest request){
        return learningDataService.addLearningData(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑学习资料")
    @RequiresPermissions("parkService:serviceFlow:learningData:edit")
    public Result<String> editLearningData(@Valid @RequestBody EditLearningDataRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return learningDataService.editLearningData(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除学习资料")
    @RequiresPermissions("parkService:serviceFlow:learningData:delete")
    public Result<String> deleteLearningData(@PathVariable("id") String id){
        return learningDataService.deleteLearningData(id);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看学习资料")
    @RequiresPermissions("parkService:serviceFlow:learningData:detail")
    public Result<LearningDateResponse> detailLearningData(@PathVariable("id") String id){
        return learningDataService.detailLearningData(id);
    }
}
