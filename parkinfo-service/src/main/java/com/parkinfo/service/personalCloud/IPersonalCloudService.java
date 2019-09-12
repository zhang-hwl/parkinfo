package com.parkinfo.service.personalCloud;

import com.parkinfo.common.Result;
import com.parkinfo.request.personalCloud.AddPersonalCloudRequest;
import com.parkinfo.request.personalCloud.QueryPersonalCloudRequest;
import com.parkinfo.request.personalCloud.SetPersonalCloudRequest;
import com.parkinfo.response.personalCloud.DownloadResponse;
import com.parkinfo.response.personalCloud.PersonalCloudResponse;
import com.parkinfo.response.personalCloud.UploadFileResponse;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPersonalCloudService {

    /**
     * 查询云盘所有文件
     * @param request
     * @return
     */
    Result<Page<PersonalCloudResponse>> findAll(QueryPersonalCloudRequest request);

    /**
     * 上传文件
     * @param request
     * @return
     */
    Result<List<UploadFileResponse>> uploadFile(HttpServletRequest request);

    /**
     * 向数据库添加上传数据
     * @param request
     * @return
     */
    Result add(AddPersonalCloudRequest request);

    /**
     * 逻辑删除文件
     * @param id
     * @return
     */
    Result delete(String id);

    /**
     * 文件重命名
     * @param request
     * @return
     */
    Result set(SetPersonalCloudRequest request);

    /**
     * 下载文件
     * @param id
     * @return
     */
    Result<DownloadResponse> download(String id);
}
