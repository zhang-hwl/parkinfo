package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.Question;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 手动删除试题库
     * @param id
     * @return
     */
    Result deleteQuestion(String id);

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
     * 获取试卷详情
     * @param sheetId
     * @return
     */
    Result<AnswerSheetDetailResponse> sheetDetail(String sheetId);

    /**
     * 提交试卷
     * @param request
     * @return
     */
    Result commitExamination(CommitAnswerRequest request);


    /**
     * 分页查询试题分类
     */
    Result<Page<QuestionCategoryListResponse>> search(QueryCategoryPageRequest request);

    /**
     * 不分页查询试题分类
     */
    Result<List<QuestionCategoryListResponse>> search(QueryCategoryListRequest request);

    /**
     * 添加试题分类
     * @param request
     * @return
     */
    Result addQuestionCategory(AddQuestionCategoryRequest request);

    /**
     * 修改试题分类
     * @param request
     * @return
     */
    Result setQuestionCategory(SetQuestionCategoryRequest request);

    /**
     * 删除试题分类
     * @param id
     * @return
     */
    Result deleteQuestionCategory(String id);


}
