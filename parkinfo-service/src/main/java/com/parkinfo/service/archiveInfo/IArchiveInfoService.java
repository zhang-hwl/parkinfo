package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfo;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.request.archiveInfo.AddArchiveInfoRequest;
import com.parkinfo.request.archiveInfo.ReadRecordRequest;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArchiveInfoService {

    Result<Page<ArchiveInfoResponse>> search(QueryArchiveInfoRequest request);

    Result<List<ArchiveInfoResponse>> findAll();

    Result<ArchiveInfoCommentResponse> findById(String id);

    Result<String> addArchiveInfo(AddArchiveInfoRequest request);

    Result<String> editArchiveInfo(ArchiveInfo archiveInfo);

    Result<String> deleteArchiveInfo(String id);

    Result<String> addReadRecord(String id);

    Result<String> addComment(ReadRecordRequest request);

    Result<Page<ArchiveReadRecord>> findReadRecord(String id);

}
