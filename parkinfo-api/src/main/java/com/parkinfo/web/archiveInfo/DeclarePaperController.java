package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.DeclarePaperResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo/declare")
@Api(value = "/archiveInfo/declare", tags = {"存档资料-申报材料"})
public class DeclarePaperController {

//    @Autowired
//    private IDeclarePaperService declarePaperService;

    @PostMapping("/all")
    @ApiModelProperty(value = "根据申报材料类型查询所有文件")
    public Result<List<DeclarePaperResponse>> findAll(@RequestBody String declareType){
        return null;
    }

    @PostMapping("/search")
    @ApiModelProperty(value = "根据查询条件分页查询所有文件")
    public Result<Page<DeclarePaperResponse>> search(@RequestBody QueryArchiveInfoRequest request){
        return null;
    }

    @PostMapping("/changeStatus/{id}")
    @ApiOperation(value = "改变园区项目状态")
    //TODO
    public Result<String> changStatus(@PathVariable("id") String id){
        return null;
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询政策文件")
    public Result<DeclarePaperResponse> findById(@PathVariable("id") String id){
        return null;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除政策文件")
    public Result<String> delete(@PathVariable("id") String id){
        return null;
    }

    @PostMapping("/add")
    @ApiOperation(value = "根据政策文件类型新增政策文件")
    //TODO
    public Result<String> add(@RequestBody DeclarePaperResponse policyPaper){
        return null;
    }

    @PostMapping("/edit/{id}")
    @ApiOperation(value = "根据id编辑政策文件")
    public Result<String> edit(@PathVariable("id") String id, @RequestBody DeclarePaperResponse policyPaper){
        return null;
    }

}
