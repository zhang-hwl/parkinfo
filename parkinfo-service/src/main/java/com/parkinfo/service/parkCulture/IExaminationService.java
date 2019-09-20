package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.Question;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.AnswerSheetListResponse;
import com.parkinfo.response.parkCulture.QuestionDetailResponse;
import com.parkinfo.response.parkCulture.QuestionListResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IExaminationService {

    /**
     * 搜索试题库
     * @param request
     * @return
     */
    Result<Page<QuestionListResponse>> search(QueryQuestionListRequest request);

    /**
     * 查看试题详情
     * @param id
     * @return
     */
    Result<QuestionDetailResponse> detail(String id);
    /**
     * 手动添加试题库
     * @param request
     * @return
     */
    Result addQuestion(AddQuestionRequest request);

    /**
     * 导入试题库
     * @param file
     * @return
     */
    Result importQuestion(MultipartFile file);

    /**
     * 编辑试题
     * @param request
     * @return
     */
    Result setQuestion(SetQuestionRequest request);

    /**
     * 生成试卷
     * @param request
     * @return
     */
    Result generateExamination(GenerateExaminationRequest request);

    /**
     * 查看自己的考试
     * @param request
     * @return
     */
    Result<Page<AnswerSheetListResponse>> search(QueryAnswerSheetListRequest request);

    /**
     * 开始考试
     * @param answerSheetId
     * @return
     */
    Result startExamination(String answerSheetId);

    /**
     * 提交试卷
     * @param request
     * @return
     */
    Result commitExamination(CommitAnswerRequest request);

}
