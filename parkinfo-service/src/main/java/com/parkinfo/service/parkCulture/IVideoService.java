package com.parkinfo.service.parkCulture;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.*;
import org.springframework.data.domain.Page;

public interface IVideoService {

    /**
     * 搜索视频列表
     * @param request
     * @return
     */
    Result<Page<VideoListResponse>> search(QueryVideoListRequest request);

    /**
     * 获取视频详情
     * @param id
     * @return
     */
    Result<VideoDetailResponse> detail(String id);

    /**
     * 分页查看视频的评论
     * @param request
     * @return
     */
    Result<Page<VideoCommentListResponse>> getCommentPage(QueryVideoCommentListRequest request);

    /**
     * 添加视频评论
     * @param request
     * @return
     */
    Result addComment(AddVideoCommentRequest request);

    /**
     * 根据videoId获取视频播放地址
     * @param videoId 视频id
     * @return
     */
    Result<GetPlayInfoResponse> getPlayInfo(String videoId);

    /**
     * 查看视频观看记录
     * @param request
     * @return
     */
    Result<Page<VideoRecordListResponse>> getRecordList(QueryVideoRecordListRequest request);

    /**
     * 管理员添加视频
     * @param request
     * @return
     */
    Result addVideo(AddVideoRequest request);

    /**
     * 设置视频状态
     * @param videoId
     * @return
     */
    Result setVideoStatus(String videoId);

    /**
     * 管理员管理视频列表
     * @param request
     * @return
     */
    Result<Page<VideoManageListResponse>> manageVideo(QueryVideoManageRequest request);

    /**
     * 管理员编辑视频
     * @param request
     * @return
     */
    Result setVideo(SetVideoRequest request);

    /**
     *
     * 获取上传视频所需参数
     * @param request
     * @return
     */
    Result<CreateUploadVideoResponse> createUploadVideo(CreateUploadVideoRequest request);

    /**
     * 刷新视频上传凭证
     * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
     * @throws Exception
     */
    Result<RefreshUploadVideoResponse> refreshUploadVideo(String videoId) throws Exception;

    /**
     * 分页查询图书分类
     */
    Result<Page<VideoCategoryListResponse>> search(QueryVideoCategoryListRequest request);

    /**
     * 添加图书分类
     * @param request
     * @return
     */
    Result addVideoCategory(AddVideoCategoryRequest request);

    /**
     * 修改图书分类
     * @param request
     * @return
     */
    Result setVideoCategory(SetVideoCategoryRequest request);

    /**
     * 删除图书分类
     * @param id
     * @return
     */
    Result deleteVideoCategory(String id);
}
