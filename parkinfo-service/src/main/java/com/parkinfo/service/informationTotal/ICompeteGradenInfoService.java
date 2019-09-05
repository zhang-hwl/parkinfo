package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.CompeteGradenInfo;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-竞争园区信息
public interface ICompeteGradenInfoService {

    Result<String> addCompeteGradenInfo(CompeteGradenInfo competeGradenInfo);

    Result<String> editCompeteGradenInfo(CompeteGradenInfo competeGradenInfo);

    Result<List<PolicyTotal>> findByVersion(String version);

    Result<String> competeGradenInfoImport(MultipartFile file);

    Result<String> competeGradenInfoExport(HttpServletResponse response);

    Result<List<PolicyTotal>> findAll();

    Result<String> deleteCompeteGradenInfo(String id);

}
