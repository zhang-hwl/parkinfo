package com.parkinfo.service.parkCulture.impl;

import com.google.common.collect.Lists;
import com.parkinfo.common.Result;
import com.parkinfo.entity.parkCulture.AnswerSheet;
import com.parkinfo.entity.parkCulture.Examination;
import com.parkinfo.entity.parkCulture.Question;
import com.parkinfo.entity.parkCulture.QuestionCategory;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.parkCulture.AnswerSheetRepository;
import com.parkinfo.repository.parkCulture.ExaminationRepository;
import com.parkinfo.repository.parkCulture.QuestionCategoryRepository;
import com.parkinfo.repository.parkCulture.QuestionRepository;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.AnswerSheetListResponse;
import com.parkinfo.response.parkCulture.QuestionDetailResponse;
import com.parkinfo.response.parkCulture.QuestionListResponse;
import com.parkinfo.service.parkCulture.IExaminationService;
import com.parkinfo.token.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
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
        if (question.getCategory()!=null){
            response.setCategoryId(question.getCategory().getId());
        }
        return Result.<QuestionDetailResponse>builder().success().data(response).build();
    }



    @Override
    public Result addQuestion(AddQuestionRequest request) {
        QuestionCategory questionCategory = this.checkQuestionCategory(request.getCategoryId());
        Question question = new Question();
        BeanUtils.copyProperties(request,question);
        question.setUploader(tokenUtils.getLoginUser());
        question.setCategory(questionCategory);
        question.setDelete(false);
        question.setAvailable(true);
        questionRepository.save(question);
        return Result.builder().success().message("添加问题成功").build();
    }

    @Override
    public Result setQuestion(SetQuestionRequest request) {
        Question question = this.checkQuestion(request.getId());
        BeanUtils.copyProperties(request,question);
        QuestionCategory questionCategory = this.checkQuestionCategory(request.getCategoryId());
        question.setCategory(questionCategory);
        questionRepository.save(question);
        return Result.builder().success().message("修改问题成功").build();
    }

    @Override
    @Transactional
    public Result generateExamination(GenerateExaminationRequest request) {
        Examination examination = new Examination();
        examination.setQuestionList(new HashSet<>());
        BeanUtils.copyProperties(request,examination);
        request.getQuestions().forEach(generateQuestionRequest -> {
            Long total= questionRepository.countByCategory_IdAndQuestionTypeAndAvailableIsTrueAndDeleteIsFalse(generateQuestionRequest.getCategoryId(),generateQuestionRequest.getQuestionType());
            if (generateQuestionRequest.getCount()<total){
                throw new NormalException("设置题目数量超出题库总数");
            }
            List<Question> questionList = questionRepository.queryIdsByCategory(generateQuestionRequest.getCategoryId(),generateQuestionRequest.getQuestionType().getIndex(),generateQuestionRequest.getCount());
            examination.getQuestionList().addAll(questionList);
        });
        examinationRepository.save(examination);
        //创建答卷
        request.getUserIds().forEach(id->{
            Optional<ParkUser> parkUserOptional = parkUserRepository.findByIdAndDeleteIsFalse(id);
            if (parkUserOptional.isPresent()){
                AnswerSheet answerSheet = new AnswerSheet();
                answerSheet.setStart(false);
                answerSheet.setAnswers(null);
                answerSheet.setCorrectNum(0);
                answerSheet.setWrongNum(0);
                answerSheet.setExamination(examination);
                answerSheet.setParkUser(parkUserOptional.get());
                answerSheet.setAvailable(true);
                answerSheet.setDelete(false);
                answerSheetRepository.save(answerSheet);
            }

        });
        return Result.builder().success().message("生成试卷成功").build();
    }

    @Override
    public Result<Page<AnswerSheetListResponse>> search(QueryAnswerSheetListRequest request) {
        ParkUser parkUser = tokenUtils.getLoginUser();
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize(), Sort.Direction.DESC, "start","createTime");
        Specification<AnswerSheet> answerSheetSpecification = (Specification<AnswerSheet>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("parkUser").get("id").as(String.class), parkUser.getId()));
            predicates.add(criteriaBuilder.equal(root.get("available").as(Boolean.class), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("delete").as(Boolean.class), Boolean.FALSE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<AnswerSheet> answerSheetPage = answerSheetRepository.findAll(answerSheetSpecification,pageable);
        Page<AnswerSheetListResponse> responsePage = this.convertAnswerSheetPage(answerSheetPage);
        return Result.<Page<AnswerSheetListResponse>>builder().success().data(responsePage).build();
    }

    @Override
    @Transactional
    public Result startExamination(String answerSheetId) {
        AnswerSheet answerSheet = this.checkAnswerSheet(answerSheetId);
        Date current = new Date();
        if (answerSheet.getEndTime()!=null&&current.compareTo(answerSheet.getEndTime())>0){
            throw new NormalException("本场考试已经结束");
        }
        if (answerSheet.getStart()){
            throw new NormalException("本场考试已经开始");
        }
        answerSheet.setStart(true);
        answerSheet.setStartTime(current);
        Date endTime;
        if (answerSheet.getExamination()!=null){
            endTime = new Date(current.getTime()+1000*60*answerSheet.getExamination().getTime());
        }else {
            endTime = new Date(current.getTime()+1000*60*60);  //默认考试60分钟
        }
        answerSheet.setEndTime(endTime);
        answerSheetRepository.save(answerSheet);
        return Result.builder().success().message("开始考试成功").build();
    }

    @Override
    @Transactional
    public Result commitExamination(CommitAnswerRequest request) {
        AnswerSheet answerSheet = this.checkAnswerSheet(request.getAnswerSheetId());
        Date current = new Date();
        if (answerSheet.getEndTime()!=null&&current.compareTo(answerSheet.getEndTime())>0){
            throw new NormalException("本场考试已经结束");
        }
        if (!answerSheet.getStart()){
            throw new NormalException("请先开始考试");
        }
        answerSheet.setCorrectNum(0);
        answerSheet.setWrongNum(0);
        Map<String,String> answers = request.getAnswers();
        answers.forEach((questionId,answer)->{
            Question question = this.checkQuestion(questionId);
            if (answer.equals(question.getAnswer().toString())){
                answerSheet.setCorrectNum(answerSheet.getCorrectNum()+1);
            }else {
                answerSheet.setWrongNum(answerSheet.getWrongNum()+1);
            }
        });
        answerSheet.setAnswers(request.getAnswersJsonString());
        answerSheetRepository.save(answerSheet);
        return Result.builder().success().message("提交答卷成功").build();
    }

    private Page<QuestionListResponse> convertQuestionPage(Page<Question> questionPage) {
        List<QuestionListResponse> content = Lists.newArrayList();
        questionPage.forEach(question -> {
            QuestionListResponse response = new QuestionListResponse();
            BeanUtils.copyProperties(question,response);
            if (question.getUploader()!=null){
                response.setUploader(question.getUploader().getNickname());
            }
            content.add(response);
        });
        return new PageImpl<>(content,questionPage.getPageable(),questionPage.getTotalElements());
    }

    private Page<AnswerSheetListResponse> convertAnswerSheetPage(Page<AnswerSheet> answerSheetPage) {
        List<AnswerSheetListResponse> content = Lists.newArrayList();
        answerSheetPage.forEach(answerSheet -> {
            AnswerSheetListResponse response = new AnswerSheetListResponse();
            BeanUtils.copyProperties(answerSheet,response);
            if (answerSheet.getExamination()!=null){
                response.setName(answerSheet.getExamination().getName());
            }
            if (answerSheet.getEndTime()==null||new Date().compareTo(answerSheet.getEndTime())<0){
                response.setWrongNum(null);
                response.setCorrectNum(null);
            }
            content.add(response);
        });
        return new PageImpl<>(content,answerSheetPage.getPageable(),answerSheetPage.getTotalElements());
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
