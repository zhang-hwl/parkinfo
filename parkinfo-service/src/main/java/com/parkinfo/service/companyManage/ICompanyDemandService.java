package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.AddCompanyInfoRequest;
import com.parkinfo.request.compayManage.QueryCompanyRequest;
import com.parkinfo.request.compayManage.SetCompanyInfoRequest;
import com.parkinfo.response.companyManage.CompanyDemandResponse;
import com.parkinfo.response.companyManage.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ICompanyDemandService {

    /**
     * 导入公司信息
     * @param file
     * @return
     */
    Result companyImport(MultipartFile file);

    Result<String> companyExport(List<String> ids, HttpServletResponse response);

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
    Result<CompanyDemandResponse> query(String id);

    /**
     * 添加企业信息
     * @param request
     * @return
     */
    Result add(AddCompanyInfoRequest request);

    /**
     * 修改企业信息
     * @param request
     * @return
     */
    Result set(SetCompanyInfoRequest request);

    /**
     * 删除企业信息
     * @param id
     * @return
     */
    Result delete(String id);
}
