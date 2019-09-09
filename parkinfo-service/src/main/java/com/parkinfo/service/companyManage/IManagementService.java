package com.parkinfo.service.companyManage;

import com.parkinfo.common.Result;
import com.parkinfo.request.compayManage.QueryManagementRequest;
import com.parkinfo.request.compayManage.SetInvestmentRequest;
import com.parkinfo.response.companyManage.ManagementResponse;
import org.springframework.data.domain.Page;

public interface IManagementService {
    /**
     * 查询所有招商信息
     * @param request
     * @return
     */
    Result<Page<ManagementResponse>> findAll(QueryManagementRequest request);

    /**
     * 删除招商信息
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 修改招商信息
     * @param request
     * @return
     */
    Result set(SetInvestmentRequest request);
}
