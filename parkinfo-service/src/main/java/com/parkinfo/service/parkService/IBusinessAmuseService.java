package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.businessAmuse.AddBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.EditBusinessAmuseRequest;
import com.parkinfo.request.parkService.businessAmuse.SearchBusinessAmuseRequest;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import org.springframework.data.domain.Page;

public interface IBusinessAmuseService {
    /**
     * 分页获取商务&周边娱乐
     * @param request
     * @return
     */
    Result<Page<BusinessAmuseResponse>> searchBusinessAmuse(SearchBusinessAmuseRequest request);

    /**
     * 新增商务&周边娱乐
     * @param request
     * @return
     */
    Result<String> addBusinessAmuse(AddBusinessAmuseRequest request);

    /**
     * 编辑商务&周边娱乐
     * @param request
     * @return
     */
    Result<String> editBusinessAmuse(EditBusinessAmuseRequest request);

    /**
     * 删除商务&周边娱乐
     * @param id
     * @return
     */
    Result<String> deleteBusinessAmuse(String id);

    Result<BusinessAmuseResponse> detailBusinessAmuse(String id);

    Result<String> detailBusinessAmuseType();
}
