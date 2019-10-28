package com.parkinfo.web.informationTotal;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.TemplateField;
import com.parkinfo.enums.TemplateEnum;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.service.informationTotal.ICompeteGradenInfoService;
import com.parkinfo.service.informationTotal.IInfoTotalTemplateService;
import com.parkinfo.service.informationTotal.IInfoVersionService;
import com.parkinfo.service.informationTotal.ITemplateFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/infoTotal/competeGradenInfo")
@Api(value = "/infoTotal/competeGradenInfo", tags = {"信息统计-竞争园区信息"})
public class CompeteGradenInfoController {

    @Autowired
    private ICompeteGradenInfoService competeGradenInfoService;
    @Autowired
    private IInfoTotalTemplateService templateService;
    @Autowired
    private ITemplateFieldService templateFieldService;
    @Autowired
    private IInfoVersionService infoVersionService;

    @PostMapping("/add")
    @ApiOperation(value = "新增竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:add")
    public Result<String> addCompeteGradenInfo(@Valid @RequestBody CompeteGradenInfoRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return competeGradenInfoService.addCompeteGradenInfo(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:edit")
    public Result<String> editCompeteGradenInfo(@Valid @RequestBody CompeteGradenInfoRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<String>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return competeGradenInfoService.editCompeteGradenInfo(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:delete")
    public Result<String> deleteCompeteGradenInfo(@PathVariable("id") String id){
        return competeGradenInfoService.deleteCompeteGradenInfo(id);
    }

    @PostMapping("/search")
    @ApiOperation(value = "根据版本查询竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:search")
    public Result<Page<CompeteGradenInfoRequest>> findByVersion(@RequestBody QueryByVersionRequest request){
        return competeGradenInfoService.findByVersion(request);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入竞争园区信息")
    @RequiresPermissions(value = "infoTotal:compete:add")
    public Result<String> policyTotalImport(@RequestParam("version") String version , @RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("parkId") String parkId){
        UploadAndVersionRequest request = new UploadAndVersionRequest();
        request.setMultipartFile(multipartFile);
        request.setVersion(version);
        request.setParkId(parkId);
        return competeGradenInfoService.competeGradenInfoImport(request);
    }

    @PostMapping("/export")
    @ApiOperation(value = "下载竞争园区信息模板")
    public Result<String> export(){
        return templateService.getTemplateUrl(TemplateEnum.COMPETE.getName());
    }

    @GetMapping("/download/{id}/{parkId}")
    @ApiOperation(value = "文件导出", notes = "{用户ID}/{园区ID}")
    public void download(@PathVariable("id")String id, @PathVariable("parkId") String parkId, HttpServletResponse response){
        competeGradenInfoService.download(id, parkId, response);
    }

    @PostMapping("/find/version")
    @ApiOperation(value = "查询所有文件版本")
    public Result<List<String>> findAllVersion(){
        List<String> list = infoVersionService.findByGeneral("竞争园区信息").getData();
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
            if(StringUtils.isNotBlank(temp.getType())){
                list.add(temp.getProject());
            }
        });
        return Result.<List<String>>builder().success().data(list).build();
    }

}
