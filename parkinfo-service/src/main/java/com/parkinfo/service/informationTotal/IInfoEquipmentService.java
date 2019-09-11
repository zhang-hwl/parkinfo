package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.InfoEquipmentRequest;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-信息化设备
public interface IInfoEquipmentService {

    Result<String> add(InfoEquipmentRequest roomInfo);

    Result<String> edit(InfoEquipmentRequest roomInfo);

    Result<List<InfoEquipmentRequest>> findByVersion(String version);

    Result<String> myImport(MultipartFile file);

    Result<String> export(HttpServletResponse response);

    Result<List<InfoEquipmentRequest>> findAll();

    Result<String> delete(String id);

    void download(HttpServletResponse response, String version);

}
