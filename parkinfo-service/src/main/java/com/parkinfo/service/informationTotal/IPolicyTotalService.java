package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-政策统计
public interface IPolicyTotalService {

    Result<String> addPolicyTotal(PolicyTotal policyTotal);

    Result<String> editPolicyTotal(PolicyTotal policyTotal);

    Result<List<PolicyTotal>> findByVersion(String version);

    Result<String> policyTotalImport(MultipartFile file);

    Result<String> policyTotalExport(HttpServletResponse response);

    Result<List<PolicyTotal>> findAll();

    Result<String> deletePolicyTotal(String id);

}