package com.parkinfo.web.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.AnswerSheetListResponse;
import com.parkinfo.response.parkCulture.BookListResponse;
import com.parkinfo.response.parkCulture.QuestionDetailResponse;
import com.parkinfo.response.parkCulture.QuestionListResponse;
import com.parkinfo.service.parkCulture.IExaminationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-11 09:58
 **/
@RestController
@RequestMapping("/parkCulture/examination")
@Api(value = "/parkCulture/examination", tags = {"园区文化-考试考核"})
public class ExaminationController {

    @Autowired
    private IExaminationService examinationService;

    @PostMapping("/question/search")
    @ApiOperation(value = "搜索题库列表")
    @RequiresPermissions("parkCulture:examination:question_search")
    public Result<Page<QuestionListResponse>> search(@Valid @RequestBody QueryQuestionListRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<QuestionListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.search(request);
    }

    @PostMapping("/question/detail/{id}")
    @ApiOperation(value = "查看试题详情")
    @RequiresPermissions("parkCulture:examination:question_detail")
    public Result<QuestionDetailResponse> detail(@PathVariable("id") String id) {
        return examinationService.detail(id);
    }

    @PostMapping("/question/add")
    @ApiOperation(value = "手动添加试题库")
    @RequiresPermissions("parkCulture:examination:question_add")
    public Result addQuestion(@Valid @RequestBody AddQuestionRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<QuestionListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.addQuestion(request);
    }

    @PostMapping("/question/set")
    @ApiOperation(value = "编辑试题库")
    @RequiresPermissions("parkCulture:examination:question_set")
    public Result setQuestion(@Valid @RequestBody SetQuestionRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<QuestionListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.setQuestion(request);
    }

    @PostMapping("/generate")
    @ApiOperation(value = "生成试卷")
    @RequiresPermissions("parkCulture:examination:examination_generate")
    public Result generateExamination(@Valid @RequestBody GenerateExaminationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<QuestionListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.generateExamination(request);
    }

    @PostMapping("/answerSheet/search")
    @ApiOperation(value = "生成试卷")
    @RequiresPermissions("parkCulture:examination:answerSheet_search")
    public Result<Page<AnswerSheetListResponse>> search(@Valid @RequestBody QueryAnswerSheetListRequest request, BindingResult result) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<AnswerSheetListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.search(request);
    }

    @PostMapping("/answerSheet/start/{answerSheetId}")
    @ApiOperation(value = "开始考试")
    @RequiresPermissions("parkCulture:examination:answerSheet_start")
    public Result startExamination(@PathVariable(value = "answerSheetId") String answerSheetId) {
        return examinationService.startExamination(answerSheetId);
    }

    @PostMapping("/answerSheet/commit")
    @ApiOperation(value = "提交答案")
    @RequiresPermissions("parkCulture:examination:answerSheet_commit")
    public Result commitExamination(@Valid @RequestBody CommitAnswerRequest request, BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return Result.<Page<AnswerSheetListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.commitExamination(request);
    }
}
