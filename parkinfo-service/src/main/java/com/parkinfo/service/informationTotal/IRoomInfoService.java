package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.BigEvent;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.entity.informationTotal.RoomInfo;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-本园区房间统计
public interface IRoomInfoService {

    Result<String> add(RoomInfoRequest roomInfo);

    Result<String> edit(RoomInfoRequest roomInfo);

    Result<List<RoomInfoRequest>> findByVersion(String version);

    Result<String> myImport(MultipartFile file);

    Result<String> export(HttpServletResponse response);

    Result<List<RoomInfoRequest>> findAll();

    Result<String> delete(String id);

    void download(HttpServletResponse response, String version);

}
