package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.CompeteGradenInfoRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-竞争园区信息
public interface ICompeteGradenInfoService {

    Result<String> addCompeteGradenInfo(CompeteGradenInfoRequest competeGradenInfo);

    Result<String> editCompeteGradenInfo(CompeteGradenInfoRequest competeGradenInfo);

    Result<List<CompeteGradenInfoRequest>> findByVersion(String version);

    Result<String> competeGradenInfoImport(MultipartFile file);

    Result<String> competeGradenInfoExport(HttpServletResponse response);

    Result<List<CompeteGradenInfoRequest>> findAll();

    Result<String> deleteCompeteGradenInfo(String id);

}
