package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.BigEventRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-园区大事件
public interface IBigEventService {

    Result<String> add(BigEventRequest bigEvent);

    Result<String> edit(BigEventRequest bigEvent);

    Result<Page<BigEventRequest>> findByVersion(QueryByVersionRequest request);

    Result<String> myImport(UploadAndVersionRequest request );

    Result<String> delete(String id);

    void download(String id, String parkId, HttpServletResponse response);

}
