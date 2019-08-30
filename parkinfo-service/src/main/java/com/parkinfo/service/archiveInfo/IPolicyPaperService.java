package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.request.archiveInfo.QueryArchiveInfoRequest;
import com.parkinfo.response.PolicyPaperResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPolicyPaperService {

    Result<Page<PolicyPaperResponse>> search(QueryArchiveInfoRequest request);

    Result<List<PolicyPaperResponse>> findAll(String policyType);

    Result<PolicyPaperResponse> findById(String id);

    Result<String> addPolicyPaper(PolicyPaperResponse policyPaper);

    Result<String> editPolicyPaper(String id, PolicyPaperResponse policyPaper);

    Result<String> deletePolicyPaper(String id);

}
