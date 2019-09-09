package com.parkinfo.service.parkCulture;

import com.parkinfo.common.Result;
import com.parkinfo.request.parkCulture.QueryVideoListRequest;
import com.parkinfo.response.parkCulture.VideoListResponse;
import org.springframework.data.domain.Page;

public interface IVideoService {

    /**
     * 搜索视频列表
     * @param request
     * @return
     */
    Result<Page<VideoListResponse>> search(QueryVideoListRequest request);
}
