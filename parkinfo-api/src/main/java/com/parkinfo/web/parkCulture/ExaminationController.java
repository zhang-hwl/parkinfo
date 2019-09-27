package com.parkinfo.web.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.login.ParkInfoListResponse;
import com.parkinfo.response.login.ParkUserListResponse;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.IExaminationService;
import com.parkinfo.service.parkCulture.ILibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private ILibraryService libraryService;

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

    @PostMapping("/question/delete/{id}")
    @ApiOperation(value = "手动删除试题库")
    @RequiresPermissions("parkCulture:examination:question_delete")
    public Result deleteQuestion(@PathVariable("id")String id) {
        return examinationService.deleteQuestion(id);
    }

    @PostMapping("/question/import")
    @ApiOperation(value = "一键导入试题库")
    @RequiresPermissions("parkCulture:examination:question_add")
    public Result importQuestion( MultipartFile file) {
        return examinationService.importQuestion(file);
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
    @ApiOperation(value = "查找答卷")
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

    @PostMapping("/answerSheet/detail/{sheetId}")
    @ApiOperation(value = "获取试卷详情")
    @RequiresPermissions("parkCulture:examination:answerSheet_start")
    public Result<AnswerSheetDetailResponse> sheetDetail(@PathVariable("sheetId") String sheetId) {
        return examinationService.sheetDetail(sheetId);
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


    @PostMapping("/category/list")
    @ApiOperation(value = "不分页查看试题的分类")
//    @RequiresPermissions("parkCulture:library:category_search")
    public Result<List<QuestionCategoryListResponse>> search(@RequestBody QueryCategoryListRequest request){
        return examinationService.search(request);
    }

    @PostMapping("/category/search")
    @ApiOperation(value = "管理员分页查看试题的分类")
    @RequiresPermissions("parkCulture:examination:category_search")
    public Result<Page<QuestionCategoryListResponse>> search(@Valid @RequestBody QueryCategoryPageRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<QuestionCategoryListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.search(request);
    }


    @PostMapping("/category/add")
    @ApiOperation(value = "管理员添加试题分类")
    @RequiresPermissions("parkCulture:examination:category_add")
    public Result addQuestionCategory(@Valid @RequestBody AddQuestionCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.addQuestionCategory(request);
    }

    @PostMapping("/category/set")
    @ApiOperation(value = "管理员修改试题分类")
    @RequiresPermissions("parkCulture:examination:category_set")
    public Result setQuestionCategory(@Valid @RequestBody SetQuestionCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return examinationService.setQuestionCategory(request);
    }

    @PostMapping("/category/delete/{id}")
    @ApiOperation(value = "管理员删除试题分类")
    @RequiresPermissions("parkCulture:examination:category_delete")
    public Result deleteQuestionCategory(@PathVariable("id")String id){
        return examinationService.deleteQuestionCategory(id);
    }

    @PostMapping("/park/list")
    @ApiOperation(value = "管理员获取园区列表")
    @RequiresPermissions("parkCulture:examination:examination_generate")
    public Result<List<ParkInfoListResponse>> getParkList(){
        return libraryService.getParkList();
    }

    @PostMapping("/user/list/{parkId}")
    @ApiOperation(value = "管理员获取某个园区的人员列表")
    @RequiresPermissions("parkCulture:examination:examination_generate")
    public Result<List<ParkUserListResponse>> getUserList(@PathVariable("parkId") String parkId){
        return libraryService.getUserList(parkId);
    }
}
