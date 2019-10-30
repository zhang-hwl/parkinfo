package com.parkinfo.web.personalCloud;

import com.parkinfo.common.Result;
import com.parkinfo.entity.personalCloud.CloudDisk;
import com.parkinfo.request.personalCloud.*;
import com.parkinfo.response.login.LoginResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.service.personalCloud.IPersonalCloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cloud")
@Api(value = "/personalCloud/cloud", tags = {"个人云盘-云盘管理"})
public class PersonalCloudController {

    @Autowired
    private IPersonalCloudService personalCloudService;

    @PostMapping("/findAll")
    @ApiOperation("分页查询所有文件")
    @RequiresPermissions("personalDisk:myDisk:search")
    public Result<Page<PersonalCloudResponse>> findAll(@RequestBody QueryPersonalCloudRequest request) {
        return personalCloudService.findAll(request);
    }

    @PostMapping("/uploadFile")
    @ApiOperation("上传文件")
    public Result<CloudDisk> uploadFile(HttpServletRequest request, @RequestParam("remark") String remark, @RequestParam("multipartFile") MultipartFile multipartFile) {
        UploadFileRequest fileRequest = new UploadFileRequest();
        fileRequest.setRemark(remark);
        fileRequest.setMultipartFile(multipartFile);
        return personalCloudService.uploadFile(request, fileRequest);
    }

    @PostMapping("/changeStatus")
    @ApiOperation("确认上传文件")
    public Result<String> changeStatus(@RequestBody CloudDisk request) {
        return personalCloudService.changeStatus(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除文件")
//    @RequiresPermissions("personalDisk:myDisk:delete")
    public Result<String> delete(@PathVariable("id") String id) {
        return personalCloudService.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("文件重命名")
//    @RequiresPermissions("personalDisk:myDisk:set")
    public Result<String> set(@RequestBody SetPersonalCloudRequest request) {
        return personalCloudService.set(request);
    }

    @PostMapping("/delete")
    @ApiOperation("批量删除文件")
//    @RequiresPermissions("personalDisk:myDisk:deleteAll")
    public Result deleteAll(@RequestBody DeletePersonalCloudRequest request) {
        return personalCloudService.deleteAll(request);
    }
}
