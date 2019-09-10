package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.serviceFlow.ServiceFlowImg;
import com.parkinfo.request.parkService.serviceFlow.SearchServiceFlowImgRequest;

public interface IServiceFlowService {
    /**
     * 获取园区服务流程图
     * @param request
     * @return
     */
    Result<ServiceFlowImg> searchServiceFlowImg(SearchServiceFlowImgRequest request);
}
