package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.request.archiveInfo.*;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo")
@Api(value = "/archiveInfo", tags = {"存档资料"})
public class ArchiveInfoController {

    @Autowired
    private IArchiveInfoService archiveInfoService;

    @PostMapping("/all")
    @ApiOperation(value = "查询所有文件")
    public Result<List<ArchiveInfoResponse>> findAll(){
        return archiveInfoService.findAll();
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据查询条件分页查询文件")
    public Result<Page<ArchiveInfoResponse>> search(@RequestBody QueryArchiveInfoRequest request){
        return archiveInfoService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询文件(带评论)")
    public Result<ArchiveInfoCommentResponse> findById(@PathVariable("id") String id){
        return archiveInfoService.findById(id);
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions(value = "archiveInfo:info_delete")
    @ApiOperation(value = "删除文件")
    public Result<String> delete(@PathVariable("id") String id){
        return archiveInfoService.deleteArchiveInfo(id);
    }

    @PostMapping("/add")
    @RequiresPermissions(value = "archiveInfo:info_add")
    @ApiOperation(value = "新增文件")
    public Result<String> add(@RequestBody AddArchiveInfoRequest request){
        return archiveInfoService.addArchiveInfo(request);
    }

    @PostMapping("/edit/{id}")
    @RequiresPermissions(value = "archiveInfo:info_edit")
    @ApiOperation(value = "编辑文件")
    public Result<String> edit(@PathVariable("id")String id, @RequestBody AddArchiveInfoRequest request){
        return archiveInfoService.editArchiveInfo(id, request);
    }

    @PostMapping("/add/comment")
    @ApiOperation(value = "新增评论")
    public Result<String> addComment(@RequestBody ArchiveCommentRequest archiveCommentRequest){
        return archiveInfoService.addComment(archiveCommentRequest);
    }

    @PostMapping("/add/record/{id}")
    @ApiOperation(value = "新增阅读记录")
    public Result<String> addReadRecord(@PathVariable("id") String id){
        return archiveInfoService.addReadRecord(id);
    }

    @PostMapping("/searchReadRecord")
    @ApiOperation(value = "查询阅读记录")
    public Result<Page<ArchiveReadRecord>> findReadRecord(@RequestBody ArchiveReadRecordRequest request){
        return archiveInfoService.findReadRecord(request);
    }

    @PostMapping("/download/{id}")
    @RequiresPermissions(value = "archiveInfo:download")
    @ApiOperation(value = "下载文件")
    public Result<String> download(@PathVariable("id") String id){
        Result<ArchiveInfoCommentResponse> byId = archiveInfoService.findById(id);
        return Result.<String>builder().success().data(byId.getData().getFileAddress()).build();
    }

    @PostMapping("/add/type")
    @RequiresPermissions(value = "archiveInfo:type_add")
    @ApiOperation(value = "新增存档资料类型")
    public Result<String> addType(@RequestBody ArchiveInfoTypeRequest request){
        return archiveInfoService.addType(request);
    }

    @PostMapping("/edit/type")
    @RequiresPermissions(value = "archiveInfo:type_edit")
    @ApiOperation(value = "修改存档资料类型名称")
    public Result<String> editType(@RequestBody ArchiveInfoTypeRequest request){
        return archiveInfoService.editType(request);
    }

    @PostMapping("/delete/type/{id}")
    @RequiresPermissions(value = "archiveInfo:type_delete")
    @ApiOperation(value = "删除存档资料类型")
    public Result<String> deleteType(@PathVariable("id") String id){
        return archiveInfoService.deleteType(id);
    }

    @PostMapping("/search/type/{id}")
    @RequiresPermissions(value = "archiveInfo:type_search")
    @ApiOperation(value = "查询存档资料类型")
    public Result<ArchiveInfoType> findType(@PathVariable("id") String id){
        return archiveInfoService.findType(id);
    }

}
