package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.request.archiveInfo.QueryLearnDataInfoRequest;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;
import com.parkinfo.response.archiveInfo.LearnDataInfoResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
import com.parkinfo.service.archiveInfo.IArchiveInfoTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo/menu")
@Api(value = "/archiveInfo/menu", tags = {"存档资料公共接口"})
public class ArchiveTypeInfoController {

    @Autowired
    private IArchiveInfoTypeService archiveInfoTypeService;

    @PostMapping("/type")
    @ApiOperation(value = "获取所有类型")
    public Result<List<AllArchiveInfoTypeResponse>> findAll(){
        return archiveInfoTypeService.findAll();
    }

    @PostMapping("/search")
    @ApiOperation(value = "查询学习资料")
    public Result<Page<LearnDataInfoResponse>> search(@RequestBody QueryLearnDataInfoRequest request){
        return archiveInfoTypeService.search(request);
    }

}
