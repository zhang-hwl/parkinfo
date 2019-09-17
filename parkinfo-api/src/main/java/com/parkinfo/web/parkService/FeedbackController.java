package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.response.parkService.FeedbackResponse;
import com.parkinfo.service.parkService.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parkService/feedback")
@Api(value = "/parkService/feedback", tags = {"园区服务-意见反馈"})
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping("/add")
    @ApiOperation(value = "新增对运营方意见反馈")
    @RequiresPermissions("parkService:serviceDemandInfo:feedback:add")
    public Result<String> addFeedback(@RequestBody AddFeedbackRequest request){
        return feedbackService.addFeedback(request);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除对运营方意见反馈")
    @RequiresPermissions("parkService:serviceDemandInfo:feedback:delete")
    public Result<String> deleteFeedback(@PathVariable("id") String id){
        return feedbackService.deleteFeedback(id);
    }

    @PostMapping("/detail/{id}")
    @ApiOperation(value = "查看对运营方意见反馈")
    @RequiresPermissions("parkService:serviceDemandInfo:feedback:detail")
    public Result<FeedbackResponse> detailFeedback(@PathVariable("id") String id){
        return feedbackService.detailFeedback(id);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询所有运营方意见反馈")
    @RequiresPermissions("parkService:serviceDemandInfo:feedback:list")
    public Result<List<FeedbackResponse>> list(){
        return feedbackService.findAll();
    }

}
