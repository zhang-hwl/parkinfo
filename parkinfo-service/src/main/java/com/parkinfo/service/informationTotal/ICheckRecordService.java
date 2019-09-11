package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CheckRecord;
import com.parkinfo.request.infoTotalRequest.CheckRecordRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-点检记录表
public interface ICheckRecordService {

    Result<String> addCheckRecord(CheckRecordRequest checkRecord);

    Result<String> editCheckRecord(CheckRecordRequest checkRecord);

    Result<List<CheckRecordRequest>> findByVersion(String version);

    Result<String> checkRecordImport(MultipartFile file);

    Result<String> checkRecordExport(HttpServletResponse response);

    Result<List<CheckRecordRequest>> findAll();

    Result<String> deleteCheckRecord(String id);

    void download(HttpServletResponse response, String version);

}
