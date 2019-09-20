package com.parkinfo.service.upload.impl;

import com.parkinfo.common.Result;
import com.parkinfo.service.upload.IUploadService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.tools.oss.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UploadServiceImpl implements IUploadService {

    @Autowired
    private IOssService ossService;
    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<String> FileUpload(HttpServletRequest request) {
        String parkId = tokenUtils.getCurrentParkInfo().getId();
        String realPath = "parkInfo/"+parkId;
        String url = ossService.MultipartFileUpload(request, realPath);
        return Result.<String>builder().success().data(url).build();
    }
}
