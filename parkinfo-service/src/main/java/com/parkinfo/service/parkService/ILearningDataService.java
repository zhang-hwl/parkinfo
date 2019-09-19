package com.parkinfo.service.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.request.parkService.learningData.SearchLearningDateRequest;
import org.springframework.data.domain.Page;

public interface ILearningDataService {
    /**
     * 分页获取学习资料
     * @param request
     * @return
     */
    Result<Page<LearningDateResponse>> searchLearningData(SearchLearningDateRequest request);

    /**
     * 新增学习资料
     * @param request
     * @return
     */
    Result<String> addLearningData(AddLearningDataRequest request);

    /**
     * 编辑学习资料
     * @param request
     * @return
     */
    Result<String> editLearningData(EditLearningDataRequest request);

    /**
     * 删除学习资料
     * @param id
     * @return
     */
    Result<String> deleteLearningData(String id);

    Result<LearningDateResponse> detailLearningData(String id);
}
