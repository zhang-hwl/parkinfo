package com.parkinfo.tools.oss;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

public interface IOssService {
    /**
     * 文件上传
     * @param inputStream
     * @return
     * @throws Exception
     */
    String upload(InputStream inputStream, String keyPrefix, String fileName);

    /**
     * request请求多文本上传
     * @param request
     * @param keyPrefix
     * @return
     */
    String MultipartFileUpload(HttpServletRequest request, String keyPrefix);
}
