package com.parkinfo.service.template;

import com.parkinfo.common.Result;
import com.parkinfo.request.template.ExcelTemplateRequest;
import com.parkinfo.response.template.ExcelTemplateTypeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IExcelTemplateService {
    /**
     * 上传模板
     * @param request
     * @return
     */
    Result<String> upload(ExcelTemplateRequest request);

    Result<String> uploadTest(String typeId, MultipartFile file);

    Result<List<ExcelTemplateTypeResponse>> findAllType();

}
