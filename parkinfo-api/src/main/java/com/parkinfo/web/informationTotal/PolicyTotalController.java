package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.TemplateField;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.request.personalCloud.UploadFileRequest;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import com.parkinfo.service.informationTotal.IPolicyTotalService;
import com.parkinfo.service.informationTotal.ITemplateFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private ITemplateFieldService templateFieldService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增政策统计")
    @RequiresPermissions(value = "infoTotal:policy:add")
    public Result<String> addPolicyTotal(@RequestBody PolicyTotalRequest request){
        return policyTotalService.addPolicyTotal(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑政策统计")
    @RequiresPermissions(value = "infoTotal:policy:edit")
    public Result<String> editPolicyTotal(@RequestBody PolicyTotalRequest request){
        return policyTotalService.editPolicyTotal(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除政策统计")
    @RequiresPermissions(value = "infoTotal:policy:delete")
    public Result<String> deletePolicyTotal(@PathVariable("id") String id){
        return policyTotalService.deletePolicyTotal(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询政策统计")
    @RequiresPermissions(value = "infoTotal:policy:search")
    public Result<Page<PolicyTotalRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return policyTotalService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入政策统计")
    @RequiresPermissions(value = "infoTotal:policy:add")
    public Result<String> policyTotalImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("parkId") String parkId){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        request.setParkId(parkId);
        return policyTotalService.policyTotalImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载政策统计模板")
    public Result<String> export(){
        return templateService.getTemplateUrl("政策统计");
    }

    @GetMapping("/download/{id}/{parkId}")
    @ApiOperation(value = "文件导出", notes = "{用户ID}/{园区ID}")
    public void download(@PathVariable("id")String id, @PathVariable("parkId") String parkId, HttpServletResponse response){
        policyTotalService.download(id, parkId, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("政策统计").getData();
        return Result.<List<String>>builder().success().data(list).build();
    }

    @ApiOperation(value = "查询所有模板中的类型")
    @PostMapping("/find/template/type")
    public Result<List<String>> findTemplateType(){
        List<String> list = Lists.newArrayList();
        List<TemplateField> byGeneral = templateFieldService.findByGeneral("政策统计");
        byGeneral.forEach(temp -> {
            if(StringUtils.isNotBlank(temp.getType())){
                list.add(temp.getType());
            }
        });
        return Result.<List<String>>builder().success().data(list).build();
    }

    @ApiOperation(value = "查询所有模板中的项目")
    @PostMapping("/find/template/project")
    public Result<List<String>> findTemplateProject(){
        List<String> list = Lists.newArrayList();
        List<TemplateField> byGeneral = templateFieldService.findByGeneral("政策统计");
        byGeneral.forEach(temp -> {
            if(StringUtils.isNotBlank(temp.getProject())){
                list.add(temp.getProject());
            }
        });
        return Result.<List<String>>builder().success().data(list).build();
    }


}
