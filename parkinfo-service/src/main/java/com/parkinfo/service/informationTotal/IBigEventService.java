package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-园区大事件
public interface IBigEventService {

    Result<String> addBigEvent(BigEvent bigEvent);

    Result<String> editBigEvent(BigEvent bigEvent);

    Result<List<BigEvent>> findByVersion(String version);

    Result<String> bigEventImport(MultipartFile file);

    Result<String> bigEventExport(HttpServletResponse response);

    Result<List<PolicyTotal>> findAll();

    Result<String> deleteBigEvent(String id);

}
