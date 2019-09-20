package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.ArchiveInfoType;
import com.parkinfo.entity.archiveInfo.ArchiveReadRecord;
import com.parkinfo.request.archiveInfo.*;
import com.parkinfo.response.archiveInfo.ArchiveInfoCommentResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoResponse;
import com.parkinfo.response.archiveInfo.ArchiveInfoTypeResponse;
import com.parkinfo.response.login.ParkInfoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArchiveInfoService {

    Result<Page<ArchiveInfoResponse>> search(QueryArchiveInfoRequest request);

    Result<ArchiveInfoCommentResponse> findById(String id);

    Result<String> addArchiveInfo(AddArchiveInfoRequest request);

    Result<String> editArchiveInfo(String id, AddArchiveInfoRequest request);

    Result<String> deleteArchiveInfo(String id);

    Result<String> addReadRecord(String id);

    Result<String> addComment(ArchiveCommentRequest request);

    Result<Page<ArchiveReadRecord>> findReadRecord(ArchiveReadRecordRequest request);

    Result<ArchiveInfoType> findType(String id);

    Result<List<ParkInfoResponse>> findAllPark();

    public Result<List<ArchiveInfoTypeResponse>> findAllType(String general);
}
