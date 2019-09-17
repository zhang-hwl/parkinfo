package com.parkinfo.service.upload;

import com.parkinfo.common.Result;

import javax.servlet.http.HttpServletRequest;

public interface IUploadService {

    Result<String> FileUpload(HttpServletRequest request);

}
