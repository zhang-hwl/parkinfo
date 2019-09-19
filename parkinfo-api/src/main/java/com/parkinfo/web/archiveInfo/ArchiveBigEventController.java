package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.request.archiveInfo.AddArchiveInfoRequest;
import com.parkinfo.request.archiveInfo.ArchiveCommentRequest;
import com.parkinfo.request.archiveInfo.ArchiveReadRecordRequest;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.service.archiveInfo.impl.BigEventService;
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
@RequestMapping("/archiveInfo/bigEvent")
@Api(value = "/archiveInfo/bigEvent", tags = {"存档资料-园区大事记"})
public class ArchiveBigEventController {

    @Autowired
        private BigEventService archiveInfoService;

    @PostMapping("/search")
    @RequiresPermissions("archiveInfo:bigEvent:search")
    @ApiOperation(value = "根据查询条件分页查询文件")
    public Result<Page<ArchiveInfoResponse>> search(@RequestBody QueryArchiveInfoRequest request){
        request.setGeneral("大事记");
        return archiveInfoService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询文件(带评论)")
    public Result<ArchiveInfoCommentResponse> findById(@PathVariable("id") String id){
        return archiveInfoService.findById(id);
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions(value = "archiveInfo:bigEvent:delete")
    @ApiOperation(value = "删除文件")
    public Result<String> delete(@PathVariable("id") String id){
        return archiveInfoService.deleteArchiveInfo(id);
    }

    @PostMapping("/add")
    @RequiresPermissions(value = "archiveInfo:bigEvent:add")
    @ApiOperation(value = "新增文件")
    public Result<String> add(@RequestBody AddArchiveInfoRequest request){
        request.setGeneral("大事记");
        return archiveInfoService.addArchiveInfo(request);
    }

    @PostMapping("/edit/{id}")
    @RequiresPermissions(value = "archiveInfo:bigEvent:edit")
    @ApiOperation(value = "编辑文件")
    public Result<String> edit(@PathVariable("id")String id, @RequestBody AddArchiveInfoRequest request){
        return archiveInfoService.editArchiveInfo(id, request);
    }

    @PostMapping("/add/comment")
    @ApiOperation(value = "新增评论")
    public Result<String> addComment(@Valid @RequestBody ArchiveCommentRequest archiveCommentRequest, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return archiveInfoService.addComment(archiveCommentRequest);
    }

    @PostMapping("/add/record/{id}")
    @ApiOperation(value = "新增阅读记录")
    public Result<String> addReadRecord(@PathVariable("id") String id){
        return archiveInfoService.addReadRecord(id);
    }

    @PostMapping("/searchReadRecord")
    @ApiOperation(value = "查询阅读记录")
    public Result<Page<ArchiveReadRecord>> findReadRecord(@Valid @RequestBody ArchiveReadRecordRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                return Result.<Page<ArchiveReadRecord>>builder().fail().code(500).message(allError.getDefaultMessage()).build();
            }
        }
        return archiveInfoService.findReadRecord(request);
    }

    @PostMapping("/download/{id}")
    @RequiresPermissions(value = "archiveInfo:bigEvent:download")
    @ApiOperation(value = "下载文件")
    public Result<String> download(@PathVariable("id") String id){
        Result<ArchiveInfoCommentResponse> byId = archiveInfoService.findById(id);
        return Result.<String>builder().success().data(byId.getData().getFileAddress()).build();
    }

    @PostMapping("/find/type")
    @ApiOperation(value = "获取所有类型")
    public Result<List<ArchiveInfoTypeResponse>> findAllType(){
        return archiveInfoService.findAllType("大事记");
    }

}
