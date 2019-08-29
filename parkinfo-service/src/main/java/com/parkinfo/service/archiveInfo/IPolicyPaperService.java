package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.PolicyPaper;
import com.parkinfo.request.archiveInfo.QueryPolicyPaperRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPolicyPaperService {

    Result<Page<PolicyPaper>> search(QueryPolicyPaperRequest request);

    Result<List<PolicyPaper>> findAll(String policyType);

    Result<PolicyPaper> findById(String id);

    Result<String> addPolicyPaper(PolicyPaper policyPaper);

    Result<String> editPolicyPaper(String id, PolicyPaper policyPaper);

    Result<String> deletePolicyPaper(String id);

}
