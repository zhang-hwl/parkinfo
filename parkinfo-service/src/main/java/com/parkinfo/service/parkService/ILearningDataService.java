package com.parkinfo.service.parkService;

import antlr.collections.impl.LList;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkService.learningData.LearnDataType;
import com.parkinfo.request.archiveInfo.ArchiveCommentRequest;
import com.parkinfo.request.archiveInfo.ArchiveReadRecordRequest;
import com.parkinfo.request.base.PageRequest;
import com.parkinfo.request.parkService.learningData.AddLearningDataRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.request.parkService.learningData.LearnDataTypeRequest;
import com.parkinfo.response.parkService.LearnDataCommentResponse;
import com.parkinfo.response.parkService.LearnDataTypeResponse;
import com.parkinfo.response.parkService.LearningDateResponse;
import com.parkinfo.request.parkService.learningData.SearchLearningDateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

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

    Result<LearnDataCommentResponse> detailLearningData(String id);

    //查询学习资料类型
    Result<List<LearnDataTypeResponse>> findAllType();

    //查询学习资料类型
    Result<Page<LearnDataTypeResponse>> searchType(PageRequest request);

    //新增学习资料类型
    Result<String> addType(LearnDataTypeRequest request);

    //编辑
    Result<String> editType(LearnDataTypeRequest request);


    Result<String> deleteType(String id);

    //新增评论
    Result<String> addComment(ArchiveCommentRequest archiveCommentRequest);

    //新增阅读记录
    Result<String> addReadRecord(String id);

//    //查询阅读记录
//    Result<Page<LearnReadRecord>> findReadRecord(ArchiveReadRecordRequest request);

}
