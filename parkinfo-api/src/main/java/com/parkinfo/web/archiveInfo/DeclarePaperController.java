package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.DeclarePaper;
import com.parkinfo.request.archiveInfo.QueryDeclarePaperRequest;
import com.parkinfo.service.archiveInfo.IDeclarePaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/archiveInfo/declare")
@Api(value = "/api/archiveInfo/declare", tags = {"存档资料-申报材料"})
public class DeclarePaperController {

    @Autowired
    private IDeclarePaperService declarePaperService;

    @PostMapping("/all")
    @ApiModelProperty(value = "根据申报材料类型查询所有文件")
    public Result<List<DeclarePaper>> findAll(@RequestBody String declareType){
        return declarePaperService.findAll(declareType);
    }

    @PostMapping("/search")
    @ApiModelProperty(value = "根据查询条件分页查询所有文件")
    public Result<Page<DeclarePaper>> search(@RequestBody QueryDeclarePaperRequest request){
        return declarePaperService.search(request);
    }

    @PostMapping("/changeStatus/{id}")
    @ApiOperation(value = "改变园区项目状态")
    //TODO
    public Result<String> changStatus(@PathVariable("id") String id){
        return declarePaperService.changeStatus(id);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询政策文件")
    public Result<DeclarePaper> findById(@PathVariable("id") String id){
        return declarePaperService.findById(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除政策文件")
    public Result<String> delete(@PathVariable("id") String id){
        return declarePaperService.deleteDeclarePaper(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "根据政策文件类型新增政策文件")
    //TODO
    public Result<String> add(@RequestBody DeclarePaper policyPaper){
        return declarePaperService.addDeclarePaper(policyPaper);
    }

    @PostMapping("/edit/{id}")
    @ApiOperation(value = "根据id编辑政策文件")
    public Result<String> edit(@PathVariable("id") String id, @RequestBody DeclarePaper policyPaper){
        return declarePaperService.editDeclarePaper(id, policyPaper);
    }

}
