package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-竞争园区信息
public interface ICompeteGradenInfoService {

    Result<String> addCompeteGradenInfo(CompeteGradenInfoRequest competeGradenInfo);

    Result<String> editCompeteGradenInfo(CompeteGradenInfoRequest competeGradenInfo);

    Result<Page<CompeteGradenInfoRequest>> findByVersion(QueryByVersionRequest request);

    Result<String> competeGradenInfoImport(UploadAndVersionRequest request);

    Result<String> deleteCompeteGradenInfo(String id);

    void download(String id, HttpServletResponse response);

}
