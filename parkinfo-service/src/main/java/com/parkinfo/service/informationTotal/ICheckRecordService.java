package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CheckRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-点检记录表
public interface ICheckRecordService {

    Result<String> addCheckRecord(CheckRecord checkRecord);

    Result<String> editCheckRecord(CheckRecord checkRecord);

    Result<List<CheckRecord>> findByVersion(String version);

    Result<String> checkRecordImport(MultipartFile file);

    Result<String> checkRecordExport(HttpServletResponse response);

    Result<List<CheckRecord>> findAll();

    Result<String> deleteCheckRecord(String id);

}
