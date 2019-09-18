package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;

import java.util.List;

public interface IArchiveInfoTypeService {

    Result<List<AllArchiveInfoTypeResponse>> findAll();
}
