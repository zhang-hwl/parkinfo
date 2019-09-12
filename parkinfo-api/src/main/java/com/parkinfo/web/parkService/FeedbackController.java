package com.parkinfo.web.parkService;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkService.feedback.AddFeedbackRequest;
import com.parkinfo.request.parkService.learningData.EditLearningDataRequest;
import com.parkinfo.service.parkService.IFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
