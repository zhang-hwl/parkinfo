package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-点检记录表
public interface ICheckRecordService {

    Result<String> addCheckRecord(CheckRecordRequest checkRecord);

    Result<String> editCheckRecord(CheckRecordRequest checkRecord);

    Result<Page<CheckRecordRequest>> findByVersion(QueryByVersionRequest request);

    Result<String> checkRecordImport(UploadAndVersionRequest request);

    Result<String> deleteCheckRecord(String id);

    void download(String id, HttpServletResponse response);

}
