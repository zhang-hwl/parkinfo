package com.parkinfo.web.parkCulture;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import com.parkinfo.service.parkCulture.IVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-06 13:52
 **/
@RestController
@RequestMapping("/parkCulture/video")
@Api(value = "/parkCulture/video", tags = {"园区文化-视频培训"})
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @PostMapping("/search")
    @ApiOperation(value = "搜索视频列表")
    @RequiresPermissions("parkCulture:video:video_search")
    public Result<Page<VideoListResponse>> search(@Valid @RequestBody QueryVideoListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.search(request);
    }

    @PostMapping("/detail/{videoId}")
    @ApiOperation(value = "查看视频详情")
    @RequiresPermissions("parkCulture:video:video_detail")
    public Result<VideoDetailResponse> detail(@PathVariable("videoId") String videoId){
        return videoService.detail(videoId);
    }

    @PostMapping("/comment/search")
    @ApiOperation(value = "分页查看视频的评论")
    @RequiresPermissions("parkCulture:video:comment_search")
    public Result<Page<VideoCommentListResponse>> getCommentPage(@Valid @RequestBody QueryVideoCommentListRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoCommentListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.getCommentPage(request);
    }

    @PostMapping("/comment/add")
    @ApiOperation(value = "添加视频评论")
    @RequiresPermissions("parkCulture:video:comment_add")
    public  Result addComment(@Valid @RequestBody AddVideoCommentRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.addComment(request);
    }

    @PostMapping("/getPlayInfo/{videoId}")
    @ApiOperation(value = "根据videoId获取视频播放地址")
    @RequiresPermissions("parkCulture:video:video_playInfo")
    public Result<GetPlayInfoResponse> getPlayInfo(@PathVariable("videoId") String videoId){
        return videoService.getPlayInfo(videoId);
    }

    @PostMapping("/record")
    @ApiOperation(value = "查看视频观看记录")
    @RequiresPermissions("parkCulture:video:record_search")
    public Result<Page<VideoRecordListResponse>> getRecordList(@Valid @RequestBody QueryVideoRecordListRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoRecordListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.getRecordList(request);
    }

    @PostMapping("/manage")
    @ApiOperation(value = "管理员-管理员管理视频列表")
    @RequiresPermissions("parkCulture:video:video_manage")
    Result<Page<VideoManageListResponse>> manageVideo(@Valid @RequestBody QueryVideoManageRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoManageListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.manageVideo(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "管理员-添加视频")
    @RequiresPermissions("parkCulture:video:video_add")
    public Result addVideo(@Valid @RequestBody AddVideoRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoRecordListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.addVideo(request);
    }

    @PostMapping("/status/{videoId}")
    @ApiOperation(value = "管理员-修改视频状态")
    @RequiresPermissions("parkCulture:video:video_setStatus")
    public Result setVideoStatus(@PathVariable("videoId") String videoId){
        return videoService.setVideoStatus(videoId);
    }

    @PostMapping("/set")
    @ApiOperation(value = "管理员-编辑视频")
    @RequiresPermissions("parkCulture:video:video_set")
    public Result setVideo(@Valid @RequestBody SetVideoRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoRecordListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.setVideo(request);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "管理员-上传视频")
    @RequiresPermissions("parkCulture:video:video_upload")
    public Result<CreateUploadVideoResponse> createUploadVideo(@Valid @RequestBody CreateUploadVideoRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<CreateUploadVideoResponse>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.createUploadVideo(request);
    }

    @PostMapping("/refresh/{videoId}")
    @ApiOperation(value = "管理员-刷新视频上传凭证")
    @RequiresPermissions("parkCulture:video:video_upload")
    public Result<RefreshUploadVideoResponse> refreshUploadVideo(@PathVariable("videoId") String videoId) throws Exception {
      return videoService.refreshUploadVideo(videoId);
    }

    @PostMapping("/category/search")
    @ApiOperation(value = "分页查看视频的分类")
    @RequiresPermissions("parkCulture:video:category_search")
    public Result<Page<VideoCategoryListResponse>> search(@Valid @RequestBody QueryVideoCategoryPageRequest request, BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.<Page<VideoCategoryListResponse>>builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.search(request);
    }

    @PostMapping("/category/list")
    @ApiOperation(value = "不分页查看视频的分类")
//    @RequiresPermissions("parkCulture:video:category_search")
    public Result<List<VideoCategoryListResponse>> search( @RequestBody QueryVideoCategoryListRequest request){
        return videoService.search(request);
    }

    @PostMapping("/category/add")
    @ApiOperation(value = "添加视频分类")
    @RequiresPermissions("parkCulture:video:category_add")
    public Result addVideoCategory(@Valid @RequestBody AddVideoCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.addVideoCategory(request);
    }

    @PostMapping("/category/set")
    @ApiOperation(value = "修改视频分类")
    @RequiresPermissions("parkCulture:video:category_set")
    public Result setVideoCategory(@Valid @RequestBody SetVideoCategoryRequest request,BindingResult result){
        if (result.hasErrors()){
            for (ObjectError error:result.getAllErrors()) {
                return Result.builder().fail().code(500).message(error.getDefaultMessage()).build();
            }
        }
        return videoService.setVideoCategory(request);
    }

    @PostMapping("/category/delete/{id}")
    @ApiOperation(value = "删除视频分类")
    @RequiresPermissions("parkCulture:video:category_delete")
    public Result deleteVideoCategory(@PathVariable("id")String id){
        return videoService.deleteVideoCategory(id);
    }
}
