package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.ManageDetailResponse;
import com.parkinfo.response.companyManage.ManagementResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IManagementService {

    /**
     * 导入招商信息
     *
     * @param file
     * @return
     */
    Result investImport(MultipartFile file);

    /**
     * 导出招商信息
     *
     * @param response
     * @return
     */
    Result investExport(HttpServletResponse response);

    /**
     * 添加招商信息
     *
     * @param request
     * @return
     */
    Result add(AddInvestmentRequest request);

    /**
     * 查询所有招商信息
     *
     * @param request
     * @return
     */
    Result<Page<ManagementResponse>> findAll(QueryManagementRequest request);

    /**
     * 删除招商信息
     *
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 查看详细信息
     *
     * @param id
     * @return
     */
    Result<ManageDetailResponse> query(String id);

    /**
     * 修改招商信息
     *
     * @param request
     * @return
     */
    Result set(SetInvestmentRequest request);

    /**
     * 设置企业入驻
     *
     * @param id
     * @return
     */
    Result enter(String id);

    /**
     * 绑定人员
     * @param request
     * @return
     */
    Result bind(BindCompanyRequest request);
}
