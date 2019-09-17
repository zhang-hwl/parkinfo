package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.response.parkService.CompanyDataResponse;

public interface IServiceInfoService {
    /**
     * 获取服务需求信息详情
     * @return
     */
    Result<CompanyDataResponse> getCompanyDataResponse();

    Result<String> addCompanyDataResponse(CompanyDataResponse companyDataResponse);
}
