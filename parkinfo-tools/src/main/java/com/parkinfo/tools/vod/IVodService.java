package com.parkinfo.tools.vod;


import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;

public interface IVodService {

    /**
     *  获取视频上传凭证
     * @param title 视频标题。
     * 长度不超过128个字符或汉字。
     * UTF8编码。
     * @param fileName  视频源文件名。
     * 必须带扩展名，且扩展名不区分大小写。
     * 支持的扩展名参见 上传概述 的限制部分。
     * @return
     */
    CreateUploadVideoResponse createUploadVideo(String title, String fileName) throws ClientException;

    /**
     * 刷新视频上传凭证
     * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
     * @throws Exception
     */
    RefreshUploadVideoResponse refreshUploadVideo(String videoId) throws Exception;


    /**
     * 根据videoId获取视频播放地址
     * @param videoId
     * @return
     */
    GetPlayInfoResponse getPlayInfo(String videoId) throws ClientException;
}
