package com.parkinfo.web.upload;

import com.parkinfo.common.Result;
import com.parkinfo.service.upload.IUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/upload")
@Api(value = "/api/upload" ,tags = {"文件上传"})
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    @PostMapping("/")
    @RequiresAuthentication
    @ApiOperation("文件上传")
    public Result<String> freightFileUpload(HttpServletRequest request){
        return uploadService.FileUpload(request);
    }

}
