package com.parkinfo.service.sysConfig;

import com.parkinfo.common.Result;
import com.parkinfo.request.sysConfig.AddSysParkRequest;
import com.parkinfo.request.sysConfig.QuerySysParkRequest;
import com.parkinfo.response.sysConfig.SysParkInfoResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ISysParkService {

    Result<Page<SysParkInfoResponse>> findAll(QuerySysParkRequest request);

    Result<String> addPark(AddSysParkRequest request);

    Result<String> editPark(String id, AddSysParkRequest request);

    Result<String> changePark(String id);

    Result<String> deletePark(String id);


}
