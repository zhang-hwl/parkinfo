package com.parkinfo.service.parkCulture;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.VideoCommentListResponse;
import com.parkinfo.response.parkCulture.VideoDetailResponse;
import com.parkinfo.response.parkCulture.VideoListResponse;
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
     * 根据video获取视频播放地址
     * @param videoId 视频id
     * @return
     */
    Result<GetPlayInfoResponse> getPlayInfo(String videoId);

    /**
     * 管理员添加视频
     * @param request
     * @return
     */
    Result addVideo(AddVideoRequest request);

    /**
     * 管理员管理视频列表
     * @param request
     * @return
     */
    Result manageVideo(QueryVideoManageRequest request);

    /**
     * 管理员编辑视频
     * @param request
     * @return
     */
    Result setVideo(SetVideoRequest request);
}
