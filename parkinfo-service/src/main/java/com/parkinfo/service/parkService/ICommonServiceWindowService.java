package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.commonServiceWindow.AddCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.EditCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.SearchCommonServiceWindowRequest;
import com.parkinfo.response.parkService.CommonServiceWindowResponse;
import org.springframework.data.domain.Page;

public interface ICommonServiceWindowService {
    /**
     * 分页获取公共服务窗口
     * @param request
     * @return
     */
    Result<Page<CommonServiceWindowResponse>> searchCommonServiceWindow(SearchCommonServiceWindowRequest request);

    /**
     * 新增公共服务窗口
     * @param request
     * @return
     */
    Result<String> addCommonServiceWindow(AddCommonServiceWindowRequest request);

    /**
     * 编辑公共服务窗口
     * @param request
     * @return
     */
    Result<String> editCommonServiceWindow(EditCommonServiceWindowRequest request);

    /**
     * 删除公共服务窗口
     * @param id
     * @return
     */
    Result<String> deleteCommonServiceWindow(String id);
}
