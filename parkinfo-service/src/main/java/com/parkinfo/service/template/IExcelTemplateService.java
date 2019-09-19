package com.parkinfo.service.template;

import com.parkinfo.common.Result;
import com.parkinfo.entity.template.ExcelTemplate;
import com.parkinfo.request.template.AddExcelTemplateRequest;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IExcelTemplateService {
    /**
     * 上传模板
     * @param request
     * @return
     */
    Result<String> upload(HttpServletRequest request);

    /**
     * 添加到数据库
     * @param request
     * @return
     */
    Result add(AddExcelTemplateRequest request);

    /**
     * 获取所有模板
     * @return
     */
    Result<List<ExcelTemplate>> find();

    /**
     * 下载模板
     * @param id
     * @return
     */
    Result<ExcelTemplate> down(String id);
}
