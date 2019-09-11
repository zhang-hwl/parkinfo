package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-政策统计
public interface IPolicyTotalService {

    Result<String> addPolicyTotal(PolicyTotalRequest request);

    Result<String> editPolicyTotal(PolicyTotalRequest request);

    Result<List<PolicyTotalRequest>> findByVersion(String version);

    Result<String> policyTotalImport(MultipartFile file);

    Result<String> policyTotalExport(HttpServletResponse response);

    Result<List<PolicyTotalRequest>> findAll();

    Result<String> deletePolicyTotal(String id);

    void download(HttpServletResponse response, String version);

}
