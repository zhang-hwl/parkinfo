package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.*;
import com.parkinfo.response.parkCulture.LiveCommentListResponse;
import com.parkinfo.response.parkCulture.LiveListResponse;
import com.parkinfo.response.parkCulture.PullLiveUrlResponse;
import com.parkinfo.response.parkCulture.PushLiveUrlResponse;
import org.springframework.data.domain.Page;

public interface ILiveService {

    /**
     * 分页查询直播
     * @param request  查询条件
     * @return
     */
    Result<Page<LiveListResponse>> search(QueryLiveListRequest request);

    /**
     * 添加直播
     * @param request
     * @return
     */
    Result addLiveBroadcast(AddLiveBroadcastRequest request);

    /**
     * 修改直播
     * @param request
     * @return
     */
    Result setLiveBroadcast(SetLiveBroadcastRequest request);

    /**
     * 生成推流地址
     * @return
     */
    Result<PushLiveUrlResponse> generatePushLiveUrl(String id);

    /**
     * 生成拉流地址
     */
    Result<PullLiveUrlResponse> generatePullLiveUrl(String id);

    /**
     * 下架或上架直播
     * @param id
     * @return
     */
    Result disableLiveBroadcast(String id);

    /**
     * 阿里云直播状态回调
     * @param aliLiveCallBack
     * @return
     */
    Result liveCallback(AliLiveCallBack aliLiveCallBack);

    /**
     * 删除直播
     * @param id
     * @return
     */
    Result deleteLiveBroadcast(String id);

    /**
     * 分页查看直播的评论
     * @param request
     * @return
     */
    Result<Page<LiveCommentListResponse>> getCommentPage(QueryLiveCommentListRequest request);

    /**
     * 添加直播评论
     * @param request
     * @return
     */
    Result addComment(AddLiveCommentRequest request);
}
