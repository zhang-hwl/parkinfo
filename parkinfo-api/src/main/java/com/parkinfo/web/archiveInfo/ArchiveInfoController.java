package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo")
@Api(value = "/archiveInfo", tags = {"存档资料"})
public class ArchiveInfoController {

    @Autowired
    private IArchiveInfoService policyPaperService;

    @GetMapping("/all")
    @ApiOperation(value = "查询所有文件")
    public Result<List<ArchiveInfo>> findAll(){
        return policyPaperService.findAll();
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据查询条件分页查询文件")
    public Result<Page<ArchiveInfo>> search(@RequestBody QueryArchiveInfoRequest request){
        return policyPaperService.search(request);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询文件")
    public Result<ArchiveInfo> findById(@PathVariable("id") String id){
        return policyPaperService.findById(id);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除文件")
    public Result<String> delete(@PathVariable("id") String id){
        return policyPaperService.deletePolicyPaper(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "根据文件类型新增文件")
    public Result<String> add(@RequestBody ArchiveInfo archiveInfo){
        return policyPaperService.addPolicyPaper(archiveInfo);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "根据id编辑文件")
    public Result<String> edit(@RequestBody ArchiveInfo archiveInfo){
        return policyPaperService.editPolicyPaper(archiveInfo);
    }

}
