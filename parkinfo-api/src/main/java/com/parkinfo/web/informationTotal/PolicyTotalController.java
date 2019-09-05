package com.parkinfo.web.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/policyTotal")
@Api(value = "/infoTotal/policyTotal", tags = {"信息统计-政策统计"})
public class PolicyTotalController {

    @Autowired
    private IPolicyTotalService policyTotalService;

    @PostMapping("/add")
    @ApiOperation(value = "新增政策统计")
    public Result<String> addPolicyTotal(@RequestBody PolicyTotal policyTotal){
        return policyTotalService.addPolicyTotal(policyTotal);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑政策统计")
    public Result<String> editPolicyTotal(@RequestBody PolicyTotal policyTotal){
        return policyTotalService.editPolicyTotal(policyTotal);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除政策统计")
    public Result<String> deletePolicyTotal(@PathVariable("id") String id){
        return policyTotalService.deletePolicyTotal(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有政策统计")
    public Result<List<PolicyTotal>> findAll(){
        return policyTotalService.findAll();
    }

    @GetMapping("/search/{version}")
    @ApiOperation(value = "根据版本查询政策统计")
    public Result<List<PolicyTotal>> findByVersion(@PathVariable("version") String version){
        return policyTotalService.findByVersion(version);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入政策统计")
    public Result<String> policyTotalImport(@RequestBody MultipartFile multipartFile){
        return policyTotalService.policyTotalImport(multipartFile);
    }

    @GetMapping("/export")
    @ApiOperation(value = "下载政策统计模板")
    public Result<String> policyTotalExport(HttpServletResponse response){
        return policyTotalService.policyTotalExport(response);
    }

}
