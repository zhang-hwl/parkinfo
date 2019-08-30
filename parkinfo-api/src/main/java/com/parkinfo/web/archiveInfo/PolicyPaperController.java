package com.parkinfo.web.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.PolicyPaperResponse;
import com.parkinfo.service.archiveInfo.IPolicyPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archiveInfo/policy")
@Api(value = "/archiveInfo/policy", tags = {"存档资料-政策文件"})
public class PolicyPaperController {

    @Autowired
    private IPolicyPaperService policyPaperService;

    @PostMapping("/all")
    @ApiOperation(value = "根据政策文件类型查询所有文件")
    public Result<List<PolicyPaperResponse>> findAll(@RequestBody String policyType){
        return policyPaperService.findAll(policyType);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据查询条件分页查询政策文件")
    public Result<Page<PolicyPaperResponse>> search(@RequestBody QueryArchiveInfoRequest request){
        return policyPaperService.search(request);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "根据id查询政策文件")
    public Result<PolicyPaperResponse> findById(@PathVariable("id") String id){
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
    public Result<String> add(@RequestBody PolicyPaperResponse policyPaper){
        return policyPaperService.addPolicyPaper(policyPaper);
    }

    @PostMapping("/edit/{id}")
    @ApiOperation(value = "根据id编辑政策文件")
    public Result<String> edit(@PathVariable("id") String id, @RequestBody PolicyPaperResponse policyPaper){
        return policyPaperService.editPolicyPaper(id, policyPaper);
    }

}
