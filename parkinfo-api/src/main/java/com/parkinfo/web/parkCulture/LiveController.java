package com.parkinfo.web.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.ILiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @create 2019-09-12 11:21
 **/
@RestController
@Slf4j
@RequestMapping("/parkCulture/live")
@Api(value = "/parkCulture/live", tags = {"园区文化-视频直播"})
public class LiveController {
    @Autowired
    private ILiveService liveBroadcastService;

    @PostMapping("/search")
    @ApiOperation(value = "分页查询直播列表",notes = "起始页为0")
    @RequiresPermissions("parkCulture:live:live_search")
    public Result<Page<LiveListResponse>> search(@RequestBody QueryLiveListRequest request){
        return liveBroadcastService.search(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加直播")
    @RequiresPermissions("parkCulture:live:live_add")
    public Result addLiveBroadcast(@Valid @RequestBody AddLiveBroadcastRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return liveBroadcastService.addLiveBroadcast(request);
    }

    @PostMapping("/set")
    @ApiOperation(value = "修改直播")
    @RequiresPermissions("parkCulture:live:live_set")
    public Result setLiveBroadcast(@Valid @RequestBody SetLiveBroadcastRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return liveBroadcastService.setLiveBroadcast(request);
    }

    @PostMapping("/pushLiveUrl/{id}")
    @ApiOperation(value = "生成推流地址")
    @RequiresPermissions("parkCulture:live:live_generatePushLiveUrl")
    public Result<PushLiveUrlResponse> generatePushLiveUrl(@PathVariable("id") String id){
        return liveBroadcastService.generatePushLiveUrl(id);
    }

    @PostMapping("/pullLiveUrl/{id}")
    @ApiOperation(value = "生成播流地址")
    @RequiresPermissions("parkCulture:live:live_generatePullLiveUrl")
    public Result<PullLiveUrlResponse> generatePullLiveUrl(@PathVariable("id") String id){
        return liveBroadcastService.generatePullLiveUrl(id);
    }

    @PostMapping("/disable/{id}")
    @ApiOperation(value = "禁用或启用直播")
    @RequiresPermissions("parkCulture:live:live_disable")
    public Result disableLiveBroadcast(@PathVariable String id){
        return liveBroadcastService.disableLiveBroadcast(id);
    }

    @GetMapping("/callback")
    @ApiOperation(value = "阿里云回调方法  与前端无关")
    public Result liveCallback(AliLiveCallBack aliLiveCallBack){
        return liveBroadcastService.liveCallback(aliLiveCallBack);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除直播")
    @RequiresPermissions("parkCulture:live:live_delete")
    public Result deleteLiveBroadcast(@PathVariable String id) {
        return liveBroadcastService.deleteLiveBroadcast(id);
    }

    @PostMapping("/comment/search")
    @ApiOperation(value = "分页查看视频的评论")
    @RequiresPermissions("parkCulture:live:comment_search")
    public Result<Page<LiveCommentListResponse>> getCommentPage(@Valid @RequestBody QueryLiveCommentListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<LiveCommentListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return liveBroadcastService.getCommentPage(request);
    }

    @PostMapping("/comment/add")
    @ApiOperation(value = "添加视频评论")
    @RequiresPermissions("parkCulture:live:comment_add")
    public  Result addComment(@Valid @RequestBody AddLiveCommentRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return liveBroadcastService.addComment(request);
    }
}
