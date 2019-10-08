package com.parkinfo.service.upload;

import com.parkinfo.common.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IUploadService {

    Result<String> FileUpload(HttpServletRequest request);

    Result<String> fileTest(MultipartFile file);

}
