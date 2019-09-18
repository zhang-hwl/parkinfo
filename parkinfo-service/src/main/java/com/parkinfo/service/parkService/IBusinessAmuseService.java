package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.businessAmuse.BusinessAmuse;
import com.parkinfo.request.parkService.businessAmuse.*;
import com.parkinfo.response.parkService.BusinessAmuseResponse;
import com.parkinfo.response.parkService.BusinessAmuseTypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

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

    Result<List<BusinessAmuseTypeResponse>> findAllBusinessAmuseTypeByServe();

    Result<List<BusinessAmuseTypeResponse>> findAllBusinessAmuseTypeByHappy();

    Result<String> deleteBusinessAmuseType(String id);

    Result<String> editBusinessAmuseType(EditBusinessAmuseTypeRequest request);

    Result<String> addBusinessAmuseHappyType(AddBusinessAmuseTypeRequest request);

    Result<String> addBusinessAmuseServeType(AddBusinessAmuseTypeRequest request);
}
