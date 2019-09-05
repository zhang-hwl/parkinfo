package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArchiveInfoService {

    Result<Page<ArchiveInfo>> search(QueryArchiveInfoRequest request);

    Result<List<ArchiveInfo>> findAll();

    Result<ArchiveInfo> findById(String id);

    Result<String> addPolicyPaper(ArchiveInfo archiveInfo);

    Result<String> editPolicyPaper(ArchiveInfo archiveInfo);

    Result<String> deletePolicyPaper(String id);

}
