package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-园区大事件
public interface IBigEventService {

    Result<String> add(BigEventRequest bigEvent);

    Result<String> edit(BigEventRequest bigEvent);

    Result<List<BigEventRequest>> findByVersion(String version);

    Result<String> myImport(MultipartFile file);

    Result<String> export(HttpServletResponse response);

    Result<List<BigEventRequest>> findAll();

    Result<String> delete(String id);

    void download(HttpServletResponse response, String version);

}
