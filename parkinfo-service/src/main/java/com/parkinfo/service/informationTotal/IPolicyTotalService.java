package com.parkinfo.service.informationTotal;

import com.parkinfo.common.Result;
import com.parkinfo.entity.informationTotal.PolicyTotal;
import com.parkinfo.request.infoTotalRequest.PolicyTotalRequest;
import com.parkinfo.request.infoTotalRequest.QueryByVersionRequest;
import com.parkinfo.request.infoTotalRequest.UploadAndVersionRequest;
import com.parkinfo.response.sysConfig.SimpleParkResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//信息统计-政策统计
public interface IPolicyTotalService {

    Result<String> addPolicyTotal(PolicyTotalRequest request);

    Result<String> editPolicyTotal(PolicyTotalRequest request);

    Result<Page<PolicyTotalRequest>> findByVersion(QueryByVersionRequest request);

    Result<String> policyTotalImport(UploadAndVersionRequest request);

    Result<String> deletePolicyTotal(String id);

    void download(String id, String parkId, HttpServletResponse response);

    //获取园区
    Result<List<SimpleParkResponse>> findAll();

}
