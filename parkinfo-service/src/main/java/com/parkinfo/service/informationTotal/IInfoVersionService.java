package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.InfoVersionResponse;

import java.util.List;

public interface IInfoVersionService {

    Result<String> add(InfoVersionResponse response);

    Result<List<String>> findByGeneral(String general);

}
