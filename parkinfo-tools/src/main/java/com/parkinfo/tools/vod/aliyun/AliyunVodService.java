package com.parkinfo.tools.vod.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.parkinfo.tools.vod.IVodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-07-24 10:31
 **/
@Component
@Slf4j
public class AliyunVodService implements IVodService {

    @Autowired
    private DefaultAcsClient vodClient;

    @Override
    public CreateUploadVideoResponse createUploadVideo(String title, String fileName) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(fileName);
//        JSONObject userData = new JSONObject();
//        JSONObject messageCallback = new JSONObject();
//        messageCallback.put("CallbackURL", "http://xxxxx");
//        messageCallback.put("CallbackType", "http");
//        userData.put("MessageCallback", messageCallback.toJSONString());
//        JSONObject extend = new JSONObject();
//        extend.put("MyId", "user-defined-id");
//        userData.put("Extend", extend.toJSONString());
//        request.setUserData(userData.toJSONString());
        return vodClient.getAcsResponse(request);
    }

    @Override
    public RefreshUploadVideoResponse refreshUploadVideo(String videoId) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId);
        return vodClient.getAcsResponse(request);
    }

    @Override
    public GetPlayInfoResponse getPlayInfo(String videoId) throws ClientException {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return vodClient.getAcsResponse(request);
    }
}
