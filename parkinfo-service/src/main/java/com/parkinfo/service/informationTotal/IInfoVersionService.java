package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionRequest;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IInfoVersionService {

    Result<String> add(InfoVersionResponse response);

    Result<List<String>> findByGeneral(String general);

    Result<String> delete(String id);

    Result<Page<InfoVersionResponse>> findAll(InfoVersionRequest request);

}
