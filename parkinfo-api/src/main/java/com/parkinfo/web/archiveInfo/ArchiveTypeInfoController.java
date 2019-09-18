package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;
import com.parkinfo.service.archiveInfo.IArchiveInfoService;
import com.parkinfo.service.archiveInfo.IArchiveInfoTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo/menu")
@Api(value = "/archiveInfo/menu", tags = {"存档资料-查看菜单下分类"})
public class ArchiveTypeInfoController {

    @Autowired
    private IArchiveInfoTypeService archiveInfoTypeService;

    @RequestMapping("/type")
    @ApiOperation(value = "获取所有类型")
    public Result<List<AllArchiveInfoTypeResponse>> findAll(){
        return archiveInfoTypeService.findAll();
    }

}
