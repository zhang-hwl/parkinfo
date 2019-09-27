package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.commonServiceWindow.CommonServiceWindowType;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.parkService.commonServiceWindow.AddCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.CommonServiceWindowTypeRequest;
import com.parkinfo.request.parkService.commonServiceWindow.EditCommonServiceWindowRequest;
import com.parkinfo.request.parkService.commonServiceWindow.SearchCommonServiceWindowRequest;
import com.parkinfo.response.parkService.CommonServiceWindowResponse;
import com.parkinfo.response.parkService.CommonServiceWindowTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

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

    Result<CommonServiceWindowResponse> detail(String id);

    Result<List<CommonServiceWindowTypeResponse>> findAllType();

    Result<String> addType(CommonServiceWindowTypeRequest type);

    Result<String> editType(CommonServiceWindowTypeRequest type);

    Result<String> deleteType(String id);

    //分页查询类型
    Result<Page<CommonServiceWindowTypeResponse>> findAllTypePage(PageRequest request);
}
