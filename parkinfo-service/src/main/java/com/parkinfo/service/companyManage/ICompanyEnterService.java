package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.*;
import com.parkinfo.response.companyManage.EnterDetailResponse;
import com.parkinfo.response.companyManage.EnterResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICompanyEnterService {

    /**
     * 导出入驻企业信息
     *
     * @param response
     * @return
     */
    Result enterExport(HttpServletResponse response);

    /**
     * 查询所有入驻后企业
     * @param request
     * @return
     */
    Result<Page<EnterResponse>> findAll(QueryEnterRequest request);

    /**
     * 修改入驻企业详情
     * @param request
     * @return
     */
    Result setCompany(ModifyCompanyRequest request);

    /**
     * 查询入驻企业详情
     * @param id
     * @return
     */
    Result<EnterDetailResponse> query(String id);

    /**
     * 删除入驻企业信息
     * @param id
     * @return
     */
    Result delete(String id);
}
