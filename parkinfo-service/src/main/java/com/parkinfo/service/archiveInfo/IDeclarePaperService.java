package com.parkinfo.service.archiveInfo;

import com.parkinfo.common.Result;
import com.parkinfo.entity.archiveInfo.DeclarePaper;
import com.parkinfo.request.archiveInfo.QueryDeclarePaperRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDeclarePaperService {

    Result<Page<DeclarePaper>> search(QueryDeclarePaperRequest request);

    Result<List<DeclarePaper>> findAll(String declareType);

    Result<DeclarePaper> findById(String id);

    Result<String> addDeclarePaper(DeclarePaper policyPaper);

    Result<String> editDeclarePaper(String id, DeclarePaper policyPaper);

    Result<String> deleteDeclarePaper(String id);

    Result<String> changeStatus(String id);

}
