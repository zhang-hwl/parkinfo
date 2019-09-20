package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.request.archiveInfo.QueryLearnDataInfoRequest;
import com.parkinfo.response.archiveInfo.AllArchiveInfoTypeResponse;
import com.parkinfo.response.archiveInfo.LearnDataInfoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArchiveInfoTypeService {

    Result<List<AllArchiveInfoTypeResponse>> findAll();

    Result<Page<LearnDataInfoResponse>> search(QueryLearnDataInfoRequest request);
}
