package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.request.infoTotalRequest.InfoEquipmentRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.RoomInfoRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-信息化设备
public interface IInfoEquipmentService {

    Result<String> add(InfoEquipmentRequest roomInfo);

    Result<String> edit(InfoEquipmentRequest roomInfo);

    Result<Page<InfoEquipmentRequest>> findByVersion(QueryByVersionRequest request);

    Result<String> myImport(UploadAndVersionRequest request);

    Result<String> delete(String id);

    void download(String id, String parkId, HttpServletResponse response);

}
