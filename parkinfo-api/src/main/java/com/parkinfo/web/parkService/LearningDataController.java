package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.request.archiveInfo.ArchiveCommentRequest;
import com.parkinfo.request.archiveInfo.ArchiveReadRecordRequest;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.request.parkService.learningData.LearnDataTypeRequest;
import com.parkinfo.response.parkService.LearnDataCommentResponse;
import com.parkinfo.response.parkService.LearnDataTypeResponse;
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
import java.util.List;

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
    @ApiOperation(value = "查看学习资料(带评论)")
    @RequiresPermissions("parkService:serviceFlow:learningData:detail")
    public Result<LearnDataCommentResponse> detailLearningData(@PathVariable("id") String id){
        return learningDataService.detailLearningData(id);
    }

    @PostMapping("/find/type")
    @ApiOperation(value = "查看学习资料类型")
    public Result<List<LearnDataTypeResponse>> findLearnDataType(){
        return learningDataService.findAllType();
    }

    @PostMapping("/add/type")
    @ApiOperation(value = "新增学习资料类型", notes = "小类名称为空时,新增大类")
    public Result<String> addType(@RequestBody LearnDataTypeRequest request){
        return learningDataService.addType(request);
    }

    @PostMapping("/edit/type")
    @ApiOperation(value = "编辑学习资料类型",notes = "小类id为空时,大类")
    public Result<String> editType(@RequestBody LearnDataTypeRequest request){
        return learningDataService.editType(request);
    }

    @PostMapping("/delete/type/{id}")
    @ApiOperation(value = "删除学习资料类型")
    public Result<String> deleteType(@PathVariable("id") String id){
        return learningDataService.deleteType(id);
    }

    @PostMapping("/add/comment")
    @ApiOperation(value = "新增评论")
    public Result<String> addComment(@Valid @RequestBody ArchiveCommentRequest archiveCommentRequest, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return learningDataService.addComment(archiveCommentRequest);
    }

//    @PostMapping("/add/record/{id}")
//    @ApiOperation(value = "新增阅读记录")
//    public Result<String> addReadRecord(@PathVariable("id") String id){
//        return learningDataService.addReadRecord(id);
//    }
//
//    @PostMapping("/searchReadRecord")
//    @ApiOperation(value = "查询阅读记录")
//    public Result<Page<LearnReadRecord>> findReadRecord(@Valid @RequestBody ArchiveReadRecordRequest request, BindingResult result){
//        if (result.hasErrors()){
//            for (ObjectError allError : result.getAllErrors()) {
//                return Result.<Page<LearnReadRecord>>builder().fail().code(500).message(allError.getDefaultMessage()).build();
//            }
//        }
//        return learningDataService.findReadRecord(request);
//    }

}
