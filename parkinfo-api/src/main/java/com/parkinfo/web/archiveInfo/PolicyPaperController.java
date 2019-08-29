package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.PolicyPaper;
import com.parkinfo.request.archiveInfo.QueryPolicyPaperRequest;
import com.parkinfo.service.archiveInfo.IPolicyPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/archiveInfo/policy")
@Api(value = "/api/archiveInfo/policy", tags = {"存档资料-政策文件"})
public class PolicyPaperController {

    @Autowired
    private IPolicyPaperService policyPaperService;

    @PostMapping("/all")
    @ApiOperation(value = "根据政策文件类型查询所有文件")
    public Result<List<PolicyPaper>> findAll(@RequestBody String policyType){
        return policyPaperService.findAll(policyType);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据查询条件分页查询政策文件")
    public Result<Page<PolicyPaper>> search(@RequestBody QueryPolicyPaperRequest request){
        return policyPaperService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询政策文件")
    public Result<PolicyPaper> findById(@PathVariable("id") String id){
        return policyPaperService.findById(id);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除政策文件")
    public Result<String> delete(@PathVariable("id") String id){
        return policyPaperService.deletePolicyPaper(id);
    }

    @PostMapping("/add")
    @ApiOperation(value = "根据政策文件类型新增政策文件")
    //TODO
    public Result<String> add(@RequestBody PolicyPaper policyPaper){
        return policyPaperService.addPolicyPaper(policyPaper);
    }

    @PostMapping("/edit/{id}")
    @ApiOperation(value = "根据id编辑政策文件")
    public Result<String> edit(@PathVariable("id") String id, @RequestBody PolicyPaper policyPaper){
        return policyPaperService.editPolicyPaper(id, policyPaper);
    }

}
