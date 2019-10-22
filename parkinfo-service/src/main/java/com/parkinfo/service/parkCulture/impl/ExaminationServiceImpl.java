package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.AnswerSheet;
import com.parkinfo.entity.parkCulture.Examination;
import com.parkinfo.entity.parkCulture.Question;
import com.parkinfo.entity.parkCulture.QuestionCategory;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.enums.AnswerSheetType;
import com.parkinfo.enums.QuestionType;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.AnswerSheetRepository;
import com.parkinfo.repository.parkCulture.ExaminationRepository;
import com.parkinfo.repository.parkCulture.QuestionCategoryRepository;
import com.parkinfo.repository.parkCulture.QuestionRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.IExaminationService;
import com.parkinfo.token.TokenUtils;
import com.parkinfo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.*;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 15:07
 **/
@Service
@Slf4j
public class ExaminationServiceImpl implements IExaminationService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerSheetRepository answerSheetRepository;

    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private QuestionCategoryRepository questionCategoryRepository;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Result<Page<QuestionListResponse>> search(QueryQuestionListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<Question> questionSpecification = (Specification<Question>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getCategoryId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id").as(String.class), request.getCategoryId()));
            }
            if (request.getQuestionType()!=null){
                predicates.add(criteriaBuilder.equal(root.get("questionType"), request.getQuestionType()));
            }
            if (StringUtils.isNotBlank(request.getQuestion())){
                predicates.add(criteriaBuilder.like(root.get("question").as(String.class), "%"+request.getQuestion()+"%"));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Question> questionPage = questionRepository.findAll(questionSpecification, pageable);
        Page<QuestionListResponse> responsePage = this.convertQuestionPage(questionPage);
        return Result.<Page<QuestionListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    public Result<QuestionDetailResponse> detail(String id) {
        Question question = this.checkQuestion(id);
        QuestionDetailResponse response = new QuestionDetailResponse();
        BeanUtils.copyProperties(question, response);
        if (question.getCategory() != null) {
            response.setCategoryId(question.getCategory().getId());
        }
        return Result.<QuestionDetailResponse>builder().success().data(response).build();
    }


    @Override
    public Result addQuestion(AddQuestionRequest request) {
        QuestionCategory questionCategory = this.checkQuestionCategory(request.getCategoryId());
        Question question = new Question();
        BeanUtils.copyProperties(request, question);
        question.setUploader(tokenUtils.getLoginUser());
        question.setCategory(questionCategory);
        question.setDelete(false);
        question.setAvailable(true);
        questionRepository.save(question);
        return Result.builder().success().message("添加问题成功").build();
    }

    @Override
    public Result deleteQuestion(String id) {
        Question question = this.checkQuestion(id);
        question.setDelete(true);
        questionRepository.save(question);
        return Result.builder().success().message("删除试题成功").build();
    }

    @Override
    public Result importQuestion(MultipartFile file) {
        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        if (!Objects.requireNonNull(fileName).matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NormalException("上传文件格式不正确");
        }
        try {
            List<AddQuestionRequest> requestList = ExcelUtils.importExcel(file, AddQuestionRequest.class);
            requestList.forEach(request -> {
                        Question question = new Question();
                        BeanUtils.copyProperties(request, question);
                        question.setUploader(tokenUtils.getLoginUser());
                        question.setDelete(false);
                        question.setAvailable(true);
                        questionRepository.save(question);
                    }
            );
        } catch (IOException e) {
            throw new NormalException("上传文件内容不符合要求");
        }
        return Result.builder().success().message("导入成功").build();
    }

    @Override
    public Result setQuestion(SetQuestionRequest request) {
        Question question = this.checkQuestion(request.getId());
        BeanUtils.copyProperties(request, question);
        QuestionCategory questionCategory = this.checkQuestionCategory(request.getCategoryId());
        question.setCategory(questionCategory);
        questionRepository.save(question);
        return Result.builder().success().message("修改问题成功").build();
    }

    @Override
    @Transactional
    public Result generateExamination(GenerateExaminationRequest request) {
        ParkUser currentUser = tokenUtils.getLoginUser();
        Examination examination = new Examination();
        examination.setQuestionList(new HashSet<>());
        BeanUtils.copyProperties(request, examination);
        examination.setAvailable(true);
        examination.setDelete(false);
        request.getQuestions().forEach(generateQuestionRequest -> {
            Long total = questionRepository.countByCategory_IdAndQuestionTypeAndAvailableIsTrueAndDeleteIsFalse(generateQuestionRequest.getCategoryId(), generateQuestionRequest.getQuestionType());
            if (generateQuestionRequest.getCount() > total) {
                throw new NormalException("设置题目数量超出题库总数");
            }
            List<Question> questionList = questionRepository.queryIdsByCategory(generateQuestionRequest.getCategoryId(), generateQuestionRequest.getQuestionType().getIndex(), generateQuestionRequest.getCount());
            examination.getQuestionList().addAll(questionList);
        });
        examinationRepository.save(examination);
        //创建答卷
        request.getUserIds().forEach(id -> {
            Optional<ParkUser> parkUserOptional = parkUserRepository.findByIdAndDeleteIsFalse(id);
            if (parkUserOptional.isPresent()) {
                AnswerSheet answerSheet = new AnswerSheet();
                answerSheet.setStart(false);
                answerSheet.setTime(request.getTime());
                answerSheet.setAnswers(null);
                answerSheet.setCorrectNum(0);
                answerSheet.setWrongNum(0);
                answerSheet.setExamination(examination);
                answerSheet.setParkUser(parkUserOptional.get());
                answerSheet.setCommit(false);
                answerSheet.setAvailable(true);
                answerSheet.setDelete(false);
                answerSheet.setCreator(currentUser);
                answerSheetRepository.save(answerSheet);
            }

        });
        return Result.builder().success().message("生成试卷成功").build();
    }

    @Override
    public Result<Page<AnswerSheetListResponse>> search(QueryAnswerSheetListRequest request) {
        ParkUser parkUser = tokenUtils.getLoginUser();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "start", "createTime");
        Specification<AnswerSheet> answerSheetSpecification = (Specification<AnswerSheet>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getStart()!=null){
                predicates.add(criteriaBuilder.equal(root.get("start").as(Boolean.class), request.getStart()));
            }
            if (request.getAnswerSheetType()== AnswerSheetType.MY){
                predicates.add(criteriaBuilder.equal(root.get("parkUser").get("id").as(String.class), parkUser.getId()));
            }else {
                predicates.add(criteriaBuilder.equal(root.get("creator").get("id").as(String.class), parkUser.getId()));
            }
            if (StringUtils.isNotBlank(request.getName())){
                predicates.add(criteriaBuilder.equal(root.get("examination").get("name").as(String.class), request.getName()));
            }
            if (StringUtils.isNotBlank(request.getNickname())){
                predicates.add(criteriaBuilder.equal(root.get("parkUser").get("nickname").as(String.class), request.getNickname()));
            }
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<AnswerSheet> answerSheetPage = answerSheetRepository.findAll(answerSheetSpecification, pageable);
        Page<AnswerSheetListResponse> responsePage = this.convertAnswerSheetPage(answerSheetPage);
        return Result.<Page<AnswerSheetListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    @Transactional
    public Result startExamination(String answerSheetId) {
        AnswerSheet answerSheet = this.checkAnswerSheet(answerSheetId);
        Date current = new Date();
        if (answerSheet.getEndTime() != null && current.compareTo(answerSheet.getEndTime()) > 0) {
            throw new NormalException("本场考试已经结束");
        }
        if (answerSheet.getStart()) {
            throw new NormalException("本场考试已经开始");
        }
        answerSheet.setStart(true);
        answerSheet.setStartTime(current);
        Date endTime;
        if (answerSheet.getExamination() != null) {
            endTime = new Date(current.getTime() + 1000 * 60 * answerSheet.getExamination().getTime());
        } else {
            endTime = new Date(current.getTime() + 1000 * 60 * 60);  //默认考试60分钟
        }
        answerSheet.setEndTime(endTime);
        answerSheetRepository.save(answerSheet);
        return Result.builder().success().message("开始考试成功").build();
    }

    @Override
    public Result<AnswerSheetDetailResponse> sheetDetail(String sheetId) {
        AnswerSheet answerSheet = this.checkAnswerSheet(sheetId);
        AnswerSheetDetailResponse response=this.convertAnswerSheet(answerSheet);
        List<QuestionDetailListResponse> radioQuestionList = Lists.newArrayList();
        List<QuestionDetailListResponse> judgeQuestionList = Lists.newArrayList();
        answerSheet.getExamination().getQuestionList().forEach(question -> {
            QuestionDetailListResponse questionResponse = new QuestionDetailListResponse();
            BeanUtils.copyProperties(question,questionResponse);
            if (questionResponse.getQuestionType()== QuestionType.Radio) {
                radioQuestionList.add(questionResponse);
            }
            if (questionResponse.getQuestionType()== QuestionType.JUDGE) {
                judgeQuestionList.add(questionResponse);
            }
        });
        response.setRadioQuestionList(radioQuestionList);
        response.setJudgeQuestionList(judgeQuestionList);
        response.setCurrentTime(new Date());
        return Result.<AnswerSheetDetailResponse>builder().success().data(response).build();
    }


    @Override
    @Transactional
    public Result commitExamination(CommitAnswerRequest request) {
        AnswerSheet answerSheet = this.checkAnswerSheet(request.getAnswerSheetId());
        Date current = new Date();
        if (answerSheet.getEndTime() != null && current.compareTo(answerSheet.getEndTime()) > 0) {
            throw new NormalException("本场考试已经结束");
        }
        if (answerSheet.getCommit()){
            throw new NormalException("您已交卷");
        }
        if (!answerSheet.getStart()) {
            throw new NormalException("请先开始考试");
        }
        answerSheet.setCorrectNum(0);
        answerSheet.setWrongNum(0);
        answerSheet.setCommit(request.getCommit());
        Map<String, String> answers = request.getAnswers();
        answers.forEach((questionId, answer) -> {
            Question question = this.checkQuestion(questionId);
            if (answer.equals(question.getAnswer().toString())) {
                answerSheet.setCorrectNum(answerSheet.getCorrectNum() + 1);
            } else {
                answerSheet.setWrongNum(answerSheet.getWrongNum() + 1);
            }
        });
        answerSheet.setAnswers(request.convertAnswersToJsonString());
        answerSheetRepository.save(answerSheet);
        return Result.builder().success().message("提交答卷成功").build();
    }

    @Override
    public Result<Page<QuestionCategoryListResponse>> search(QueryCategoryPageRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "createTime");
        Specification<QuestionCategory> questionCategorySpecification = (Specification<QuestionCategory>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<QuestionCategory> bookCategoryPage = questionCategoryRepository.findAll(questionCategorySpecification, pageable);
        Page<QuestionCategoryListResponse> responsePage = this.convertQuestionCategoryPage(bookCategoryPage);
        return Result.<Page<QuestionCategoryListResponse>>builder().success().data(responsePage).build();
    }


    @Override
    public Result<List<QuestionCategoryListResponse>> search(QueryCategoryListRequest request) {
        Specification<QuestionCategory> queryCategorySpecification = (Specification<QuestionCategory>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<QuestionCategory> questionCategoryList = questionCategoryRepository.findAll(queryCategorySpecification);
        List<QuestionCategoryListResponse> responseList = this.convertQuestionCategoryList(questionCategoryList);
        return Result.<List<QuestionCategoryListResponse>>builder().success().data(responseList).build();
    }

    @Override
    public Result addQuestionCategory(AddQuestionCategoryRequest request) {
        QuestionCategory questionCategory = new QuestionCategory();
        BeanUtils.copyProperties(request, questionCategory);
        questionCategory.setDelete(false);
        questionCategory.setAvailable(true);
        questionCategoryRepository.save(questionCategory);
        return Result.builder().success().message("添加试题分类成功").build();
    }

    @Override
    public Result setQuestionCategory(SetQuestionCategoryRequest request) {
        QuestionCategory questionCategory = this.checkQuestionCategory(request.getId());
        BeanUtils.copyProperties(request, questionCategory);
        questionCategoryRepository.save(questionCategory);
        return Result.builder().success().message("修改试题分类成功").build();
    }

    @Override
    public Result deleteQuestionCategory(String id) {
        QuestionCategory questionCategory = this.checkQuestionCategory(id);
        questionCategory.setDelete(true);
        questionCategoryRepository.save(questionCategory);
        return Result.builder().success().message("删除图书分类成功").build();
    }

    private Page<QuestionListResponse> convertQuestionPage(Page<Question> questionPage) {
        List<QuestionListResponse> content = Lists.newArrayList();
        questionPage.forEach(question -> {
            QuestionListResponse response = new QuestionListResponse();
            BeanUtils.copyProperties(question, response);
            if (question.getUploader() != null) {
                response.setUploader(question.getUploader().getNickname());
            }
            if (question.getCategory()!=null){
                response.setCategoryId(question.getCategory().getId());
                response.setCategoryName(question.getCategory().getName());
            }
            content.add(response);
        });
        return new PageImpl<>(content, questionPage.getPageable(), questionPage.getTotalElements());
    }


    private Page<QuestionCategoryListResponse> convertQuestionCategoryPage(Page<QuestionCategory> questionCategoryPage) {
        List<QuestionCategoryListResponse> content = Lists.newArrayList();
        questionCategoryPage.forEach(questionCategory -> {
            QuestionCategoryListResponse response = new QuestionCategoryListResponse();
            BeanUtils.copyProperties(questionCategory, response);
            content.add(response);
        });
        return new PageImpl<>(content, questionCategoryPage.getPageable(), questionCategoryPage.getTotalElements());

    }

    private Page<AnswerSheetListResponse> convertAnswerSheetPage(Page<AnswerSheet> answerSheetPage) {
        List<AnswerSheetListResponse> content = Lists.newArrayList();
        answerSheetPage.forEach(answerSheet -> {
            AnswerSheetListResponse response = new AnswerSheetListResponse();
            BeanUtils.copyProperties(answerSheet, response);
            if (answerSheet.getExamination() != null) {
                response.setName(answerSheet.getExamination().getName());
            }
            //未交卷
            if (!answerSheet.getCommit()){
                //尚未开始考试或者考试还没结束
                if (answerSheet.getEndTime() == null || new Date().compareTo(answerSheet.getEndTime()) < 0) {
                    response.setWrongNum(null);
                    response.setCorrectNum(null);
                }
            }
            if (answerSheet.getParkUser()!=null){
                response.setNickname(answerSheet.getParkUser().getNickname() );
            }
            content.add(response);
        });
        return new PageImpl<>(content, answerSheetPage.getPageable(), answerSheetPage.getTotalElements());
    }


    private AnswerSheetDetailResponse convertAnswerSheet(AnswerSheet answerSheet) {
        AnswerSheetDetailResponse response = new AnswerSheetDetailResponse();
        BeanUtils.copyProperties(answerSheet, response);
        if (answerSheet.getExamination() != null) {
            response.setName(answerSheet.getExamination().getName());
        }
        if (answerSheet.getEndTime() == null || new Date().compareTo(answerSheet.getEndTime()) < 0) {
            response.setWrongNum(null);
            response.setCorrectNum(null);
        }
        if (answerSheet.getParkUser()!=null){
            response.setNickname(answerSheet.getParkUser().getNickname() );
        }
        return response;
    }

    private List<QuestionCategoryListResponse> convertQuestionCategoryList(List<QuestionCategory> questionCategoryList) {
        List<QuestionCategoryListResponse> content = Lists.newArrayList();
        questionCategoryList.forEach(questionCategory -> {
            QuestionCategoryListResponse response = new QuestionCategoryListResponse();
            BeanUtils.copyProperties(questionCategory, response);
            content.add(response);
        });
        return content;
    }


    private Question checkQuestion(String id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) {
            throw new NormalException("该试题不存在");
        }
        return questionOptional.get();
    }

    private QuestionCategory checkQuestionCategory(String categoryId) {
        Optional<QuestionCategory> questionCategoryOptional = questionCategoryRepository.findById(categoryId);
        if (!questionCategoryOptional.isPresent()) {
            throw new NormalException("该视频分类不存在");
        }
        return questionCategoryOptional.get();

    }

    private AnswerSheet checkAnswerSheet(String answerSheetId) {
        Optional<AnswerSheet> answerSheetOptional = answerSheetRepository.findById(answerSheetId);
        if (!answerSheetOptional.isPresent()) {
            throw new NormalException("该答卷不存在");
        }
        return answerSheetOptional.get();
    }

}
