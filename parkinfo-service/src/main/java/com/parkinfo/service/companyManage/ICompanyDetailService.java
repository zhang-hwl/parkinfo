package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.request.compayManage.SetCompanyRequireRequest;
import com.parkinfo.response.companyManage.CompanyDetailResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface ICompanyDetailService {

    /**
     * 导入公司信息
     * @param file
     * @return
     */
    Result companyImport(MultipartFile file);

    /**
     * 导出公司信息
     * @param response
     * @return
     */
    Result companyExport(HttpServletResponse response);

    /**
     * 查询所有企业信息
     * @param request
     * @return
     */
    Result<Page<CompanyResponse>> findAll(QueryCompanyRequest request);

    /**
     * 查询企业详情
     * @param id
     * @return
     */
    Result<CompanyDetailResponse> query(String id);

    /**
     * 修改企业信息
     * @param request
     * @return
     */
    Result set(SetCompanyInfoRequest request);

    /**
     * 修改企业需求
     * @param request
     * @return
     */
    Result setRequire(SetCompanyRequireRequest request);

    /**
     * 删除企业信息
     * @param id
     * @return
     */
    Result delete(String id);
}
